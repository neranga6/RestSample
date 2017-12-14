class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			action = [GET:"show", POST:"save"]
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
