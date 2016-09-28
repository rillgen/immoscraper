package com.ludtek.immoscraper.model

import groovy.transform.*

@ToString
@Canonical
class Publication {
	
	def id
	def title
	def description
	def operation
	def amount
	def currency
	
}
