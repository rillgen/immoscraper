package com.ludtek.immoscraper.transformer

import com.ludtek.immoscraper.model.Publication

import groovy.util.slurpersupport.GPathResult;

import java.util.regex.Matcher;

import org.ccil.cowan.tagsoup.Parser

abstract class AbstractHTMLPublicationTransformer implements PublicationTransformer {

	@Override
	public Publication parse(String html) {
		def parser=new XmlSlurper(new Parser())
		parse(parser.parseText(html.trim()))
	}

	def parseMeta(GPathResult rootnode) {
		def meta = rootnode.head.meta.collectEntries {
			[
				it.@name.text()?:it.@property.text(),
				it.@content.text()
			]
		}
	}

	def getM() {
		Matcher.lastMatcher
	}
	
	public static final String sanitize(String value) {
		value?value.replaceAll("<br\\/>|<br>|\n|\t|\r|,|\\/", " "):value
	}
}
