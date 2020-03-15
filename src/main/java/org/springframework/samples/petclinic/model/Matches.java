
package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Matches {

	private List<Match> matches;


	@XmlElement
	public List<Match> getMatchesList() {
		if (this.matches == null) {
			this.matches = new ArrayList<>();
		}
		return this.matches;
	}

}
