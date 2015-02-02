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
		return this.blocked > 0;
	}

	public void decrementBlocked() {
		if (this.blocked > 0) {
			this.blocked--;
		}
	}

	public int getTurnsBlocked() {
		return blocked;
	}

	public void setBlocked(int turns) {
		this.blocked = turns;
	}

	public IPositionable getMidpoint() {
		return new IPositionable() {
			@Override
			public int getX() {
				return (station1.getLocation().getX() + station2.getLocation().getX()) / 2;
			}

			@Override
			public int getY() {
				return (station1.getLocation().getY() + station2.getLocation().getY()) / 2;
			}

			@Override
			public void setX(int x) {

			}

			@Override
			public void setY(int y) {

			}

			@Override
			public boolean equals(Object o) {
				return false;
			}
		};
	}
}