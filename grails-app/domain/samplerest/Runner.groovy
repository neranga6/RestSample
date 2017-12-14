package samplerest

class Runner {
	String firstName
	String lastName
	double age

    static constraints = {
		
    }
	
	static belongsTo = [race:Race]
}
