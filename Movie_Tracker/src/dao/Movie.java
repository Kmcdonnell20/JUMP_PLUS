package dao;

public class Movie {
	
	// private variables for movie
	private int id;
	
	private String title;
	
	private double rating;
	

	public Movie(int id, String title, double rating) {
		super();
		this.id = id;
		this.title = title;
		this.rating = rating;
	}


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return this.rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", title='" + getTitle() + "'" +
            ", rating='" + getRating() + "'" +
            "}";
    }
    

}
