package com.ludtek.immoscraper.transformer.immobilienscout;

public enum ImmobilienscoutCategory {
	 ApartmentBuy("APARTMENT_BUY", Operation.BUY, PropertyType.APARTMENT),
	 ApartmentRent("APARTMENT_RENT", Operation.RENT, PropertyType.APARTMENT),
	 AssistedLiving("ASSISTED_LIVING", Operation.RENT, PropertyType.APARTMENT),
	 CompulsoryAuction("COMPULSORY_AUCTION", Operation.BUY, PropertyType.UNKNOWN),
	 FlatShareRoom("FLAT_SHARE_ROOM", Operation.RENT, PropertyType.APARTMENT),
	 GarageBuy("GARAGE_BUY", Operation.BUY, PropertyType.GARAGE),
	 GarageRent("GARAGE_RENT", Operation.BUY, PropertyType.GARAGE),
	 Gastronomy("GASTRONOMY", Operation.BUY, PropertyType.COMMERCIAL),
	 HouseBuy("HOUSE_BUY", Operation.BUY, PropertyType.HOUSE),
	 HouseRent("HOUSE_RENT", Operation.RENT, PropertyType.HOUSE),
	 HouseType("HOUSE_TYPE", Operation.BUY, PropertyType.HOUSE),
	 Industry("INDUSTRY", Operation.BUY, PropertyType.COMMERCIAL),
	 Investment("INVESTMENT", Operation.BUY, PropertyType.UNKNOWN),
	 LivingBuySite("LIVING_BUY_SITE", Operation.BUY, PropertyType.PARCEL),
	 LivingRentSite("LIVING_RENT_SITE", Operation.RENT, PropertyType.PARCEL),
	 Office("OFFICE", Operation.UNDEFINED, PropertyType.OFFICE),
	 SeniorCare("SENIOR_CARE", Operation.RENT, PropertyType.APARTMENT),
	 ShortTermAccommodation("SHORT_TERM_ACCOMMODATION", Operation.RENT, PropertyType.APARTMENT),
	 SpecialPurpose("SPECIAL_PURPOSE", Operation.UNDEFINED, PropertyType.UNKNOWN),
	 Store("STORE", Operation.UNDEFINED, PropertyType.COMMERCIAL),
	 TradeSite("TRADE_SITE", Operation.UNDEFINED, PropertyType.PARCEL),
	 Unknown("", Operation.UNDEFINED, PropertyType.APARTMENT),;
	
	public static enum Operation {BUY, RENT, UNDEFINED};
	public static enum PropertyType {APARTMENT, HOUSE, OFFICE, COMMERCIAL, GARAGE, PARCEL, UNKNOWN};

	private final String category;
	private final Operation operation;
	private final PropertyType propertyType;
	
	private ImmobilienscoutCategory(String category, Operation operation, PropertyType propertyType) {
		this.category = category;
		this.operation = operation;
		this.propertyType = propertyType;
	}
	
	public Operation getOperation() {
		return operation;
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public String getCategory() {
		return category;
	}
	
	public static ImmobilienscoutCategory fromString(String category) {
		for(ImmobilienscoutCategory cat: ImmobilienscoutCategory.values()) {
			if(category.equals(cat.getCategory())) {
				return cat;
			}
		}
		return ImmobilienscoutCategory.Unknown;
	}
}
