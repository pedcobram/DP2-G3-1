
package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MatchRequests {

	private List<MatchRequest> matchRequests;


	@XmlElement
	public List<MatchRequest> getMatchRequestList() {
		if (this.matchRequests == null) {
			this.matchRequests = new ArrayList<>();
		}
		return this.matchRequests;
	}

}
