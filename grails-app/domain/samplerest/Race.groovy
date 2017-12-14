package samplerest

class Race {
	
	String name
	double distance

    static constraints = {
		
		name (nullable:true)
		
    }
	
	static hasMany = [runners:Runner]
}
