package com.ludtek.immoscraper.transformer.immobilienscout

import java.util.stream.FindOps.FindOp

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ludtek.immoscraper.model.GeoLocation;
import com.ludtek.immoscraper.model.Publication;
import com.ludtek.immoscraper.transformer.AbstractHTMLPublicationTransformer

import groovy.util.slurpersupport.GPathResult;

;

class ImmobilienscoutPublicationTransformer extends AbstractHTMLPublicationTransformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImmobilienscoutPublicationTransformer.class)

    @Override
    public Publication parse(GPathResult rootNode) {
        Publication parsed = new Publication()

        if (rootNode.head.title.text().contains('deaktiviert')) {
            println 'offer deactivated... skipping...'
            return parsed
        }


        def values = readKeyValues(rootNode)

        parsed.amount = (values['obj_totalRent'] ?: values['obj_baseRent'] ?: values['obj_purchasePrice'] ?: 0) as Float
        parsed.area = (values['obj_livingSpace'] ?: 0) as Float
        parsed.barrio = values['obj_regio3']
        parsed.localidad = values['obj_regio2']
        parsed.provincia = values['obj_regio1']
        parsed.currency = 'EUR'
        parsed.dormcount = (values['obj_noRooms'] ?: values['obj_noRoomsRange'] ?: -1) as Double
        parsed.id = values['obj_scoutId'] as Integer

        parsed.propertyType = values['type']
        parsed.operation = values['operation']
        parsed.category = values['category']

        parsed.provider = 'immobilienscout'

        def meta = parseMeta(rootNode)
        parsed.title = meta['og:title']
        parsed.url = "https://www.immobilienscout24.de/expose/${parsed.id}"

        parsed.location = parseGeodata(rootNode)

        def etagestr = values['obj_floor']
        try {
            parsed.etage = (etagestr ?: 0) as Integer
        } catch (NumberFormatException nfe) {
            LOGGER.warn("Could not format etage value ${etagestr}")
        }
        parsed.plz = values['geo_plz']

        def street = values['obj_streetPlain']
        def houseNumber = values['obj_houseNumber']
        parsed.adresse = street == 'no_information' ? null : street?.replaceAll('_', ' ') + ' ' + houseNumber

        parsed.balkon = toBool(values['obj_balcony'])
        parsed.keller = toBool(values['obj_cellar'])
        parsed.ebk = toBool(values['obj_hasKitchen'])
        parsed.aufzug = toBool(values['obj_lift'])
        parsed.garten = toBool(values['obj_garden'])

        parsed.description = parseDescription(rootNode)

        parsed.timestamp = new Date()

        return parsed;
    }

    def parseDescription(rootNode) {
        def desc = rootNode.body.'**'.findAll { node ->
            node.name() == 'pre'
        }.inject("") { acc, val ->
            switch (val.@class.text()) {
                case 'is24qa-objektbeschreibung':
                    acc += 'Object Beschreibung: '
                    break
                case 'is24qa-ausstattung':
                    acc += 'Ausstattung: '
                    break
                case 'is24qa-lage':
                    acc += 'Lage: '
                    break
                case 'is24qa-sonstiges':
                    acc += 'Sonstiges: '
            }

            acc += sanitize(val.text())
            acc += " | "
            acc
        }
        desc ? desc[0..-3].trim() : null
    }

    def parseGeodata(rootNode) {
        def geoMap = [:]

        rootNode.body.'**'.find { node -> node.name() == 'div' && node.@id == 'half-page-ad-stick-stopper' }?.text()
                ?.eachLine { line ->
            if (line.trim() =~ /(lat|lng): ([\d]+\.[\d]+)[,]{0,1}/) {
                def key = m[0][1]
                def value = m[0][2]
                switch (key) {
                    case 'lat':
                        geoMap['lat'] = value as double
                        break
                    case 'lng':
                        geoMap['lon'] = value as double
                        break
                }
            }

        }

        geoMap as GeoLocation
    }

    static GeoLocation validate(GeoLocation geoLocation) {
        geoLocation?.lat && geoLocation?.lon ? geoLocation : null
    }

    def toBool(value) {
        'y' == value
    }
    //APARTMENT_RENT
    def DATA_REGEX = ~/.*var keyValues = \{"(.+)"\};.*/
    def PROPERTY_REGEX = ~/.*realEstateType: \"(.+)\",.*/

    private Map readKeyValues(GPathResult rootNode) {
        def dataScript = rootNode.head.'**'.find { node ->
            node.name() == 'script' && node.@type == 'text/javascript' && node.text().indexOf("var keyValues") != -1
        }?.text()?.trim()

        def valuemap = [:]
        dataScript.eachLine { dataLine ->
            switch (dataLine) {
                case DATA_REGEX:
                    valuemap << m[0][1].split("\",\"").collectEntries { pairtxt ->
                        def pair = pairtxt.split("\":\"").collect { (it =~ "[\"]{0,1}(.+)[\"]{0,1}")[0][1] }
                        [(pair[0]): pair[1]]
                    }
                    break
                case PROPERTY_REGEX:
                    def cat = ImmobilienscoutCategory.fromString(m[0][1])
                    valuemap << ['category': cat.category]
                    valuemap << ['type': cat.propertyType]
                    valuemap << ['operation': cat.operation]
                    break
            }
        }
        return valuemap
    }

}
