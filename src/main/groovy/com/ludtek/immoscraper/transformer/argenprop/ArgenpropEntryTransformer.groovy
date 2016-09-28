package com.ludtek.immoscraper.transformer.argenprop

import java.util.List;
import org.ccil.cowan.tagsoup.Parser

import com.ludtek.immoscraper.model.PublicationEntry;
import com.ludtek.immoscraper.transformer.PublicationEntryTransformer;

class ArgenpropEntryTransformer implements PublicationEntryTransformer {

	@Override
	public List<PublicationEntry> parse(String html) {
		def parser=new XmlSlurper(new Parser())
		
		
		def rootnode = parser.parseText(html.trim())
		
//		rootnode.body.div[9].children().each {
//			println "${it.name()} - ${it.@id}"
//		}
		
//		rootnode.body.'**'.find { node ->
//			node.name() == 'div' && node.@id == 'resultadoBusqueda'	
//		}.ul.li.each{ li ->
//			li.children().each {
//				println "${it.name()} - ${it.@id}"
//			}
//		}
		
		rootnode.body.'**'.findAll { node ->
			node.name() == 'a' && node.@class == 'btn-ficha'
		}.each {
			println it.@href
		}
		
		return null;
	}
	
	//btn-ficha

}
