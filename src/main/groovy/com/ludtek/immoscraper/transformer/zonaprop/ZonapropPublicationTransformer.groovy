package com.ludtek.immoscraper.transformer.zonaprop

import groovy.util.slurpersupport.GPathResult

import com.ludtek.immoscraper.model.Publication
import com.ludtek.immoscraper.transformer.AbstractHTMLPublicationTransformer

class ZonapropPublicationTransformer extends AbstractHTMLPublicationTransformer {

	def PRICE_REGEX = ~/(.+) ([0-9\\.]+)/

	@Override
	public Publication parse(GPathResult rootnode) {
		def parsed = new Publication()
		
		def data = parseDataLayer(rootnode)
		def meta = parseMeta(rootnode)
		
		def keywords = meta['keywords']
		
		def superficiematcher = keywords =~ ".+Superficie total ([0-9]+).+"
		def ambientesmatcher = keywords =~ ".+Ambientes ([0-9]+).+"
				
		//TODO: check weird Venta/Alquiler properties
		def priceText =data.find { k, v ->
			k.startsWith("precio")
		}?.value

		parsed.with {
			switch(priceText) {
				case PRICE_REGEX:
					currency = m[0][1] == '$'?"ARS":"USD"
					amount = m[0][2].replaceAll("\\.","") as int
			}
			
			id = rootnode.body.input.find { it.@id.text() == 'sugerencia-idaviso' }.@value.text() as int
			propertyType = data['tipoDePropiedad']
			operation = data['tipoDeOperacion']
			provider = 'zonaprop'
			title = meta['og:title']
			description = sanitize(meta['og:description'])
			url = meta['og:url']
			area = superficiematcher.matches()?superficiematcher[0][1] as int:0
			dormcount = ambientesmatcher.matches()?(ambientesmatcher[0][1] as int) - 1:0
			
			def zonaprov = data['provincia'] 
			
			switch(zonaprov) {
				case "Capital Federal":
					provincia = zonaprov
					localidad = zonaprov
					partido = zonaprov
					barrio = data['ciudad']
					break;
				case it.startsWith('GBA'):
					provincia = "Buenos Aires"
					localidad = zonaprov
					partido = data['ciudad']
					barrio = data['barrio']
			}
			
		}

		return parsed;
	}

	def DATA_REGEX = ~/dataLayer\.push\(\{(".+")\}\);/

	def parseDataLayer(GPathResult rootnode) {
		//	<script type="text/javascript">
		//		dataLayer.push({"tipoDeOperacion":"Venta","tipoDePropiedad":"Departamento","provincia":"GBA Oeste","ciudad":"Morón","barrio":"Castelar","categoriaListing":"clasificados","precioVenta":"U$S 145.000","avisoOnline":"true"});
		//	</script>
		
		def dataScript = rootnode.body.'**'.find { node ->
			node.name() == 'script' && node.@type == 'text/javascript' && node.text().indexOf("dataLayer.push") != -1
		}?.text()?.trim()
		
		switch(dataScript) {
			case DATA_REGEX:
				return m[0][1].split(',').collectEntries { pairtxt ->
					def pair = pairtxt.split(':').collect { (it =~ "\"(.+)\"")[0][1] }
					[(pair[0]):pair[1]]										
				}
		}
		return [:]
	}
}
