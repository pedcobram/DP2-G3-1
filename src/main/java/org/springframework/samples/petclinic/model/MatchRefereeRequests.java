
package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MatchRefereeRequests {

	private List<MatchRefereeRequest> matchRefereeRequests;


	@XmlElement
	public List<MatchRefereeRequest> getMatchRefereeRequestList() {
		if (this.matchRefereeRequests == null) {
			this.matchRefereeRequests = new ArrayList<>();
		}
		return this.matchRefereeRequests;
	}

}
