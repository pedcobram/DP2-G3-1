
package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FootballPlayers {

	private List<FootballPlayer> footballPlayers;


	@XmlElement
	public List<FootballPlayer> getFootballPlayersList() {
		if (this.footballPlayers == null) {
			this.footballPlayers = new ArrayList<>();
		}
		return this.footballPlayers;
	}

}
