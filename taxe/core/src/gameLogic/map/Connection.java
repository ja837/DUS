package gameLogic.map;

public class Connection {
	private Station station1;
	private Station station2;
	private int blocked;

	public Connection(Station station1, Station station2) {
		this.station1 = station1;
		this.station2 = station2;
		this.blocked = 0;
	}

	public Station getStation1() {
		return this.station1;
	}

	public Station getStation2() {
		return this.station2;
	}

	public boolean isBlocked() {
		if (this.blocked > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void decrementBlocked() {
		if (this.blocked > 0) {
			this.blocked--;
		}
	}

	public void setBlocked(int turns) {
		this.blocked = turns;
	}
}