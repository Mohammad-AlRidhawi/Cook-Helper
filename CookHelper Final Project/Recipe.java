import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;


public class Recipe implements Comparable<Recipe>{
private String name;
private String catergory;
private int rating;
private LinkedList<String> directions;
private LinkedList<Ingredient> ingredients;
private String type;
private String cookingTime;
private int searchScore;

	public Recipe(String name, String catergory, String type, String cookingTime, int rating){
		this.name = name;
		this.catergory = catergory;
		this.rating = rating;
		this.type = type;
		this.cookingTime = cookingTime;
		searchScore = 0;
	}
	public Recipe(){
		
	}
	private void delegateData(String data){
		String[] toDelegate = data.split("|");
		int i = 1;
		do{
			addIngredient(toDelegate[i]);
			i++;
		}while(toDelegate[i]!="--DIRECTIONS--");
		i++;
		do{
			addDirection(toDelegate[i]);
			i++;
		}while(toDelegate.length>i);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCatergory() {
		return catergory;
	}
	public void setCatergory(String catergory) {
		this.catergory = catergory;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public LinkedList<String> getDirections() {
		return directions;
	}
	public void setDirections(LinkedList<String> directions) {
		this.directions = directions;
	}
	public LinkedList<Ingredient> getIngredients() {
		return ingredients;
	}
	public void setIngredients(LinkedList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCookingTime() {
		return cookingTime;
	}
	public void setCookingTime(String cookingTime) {
		this.cookingTime = cookingTime;
	}
	public void addIngredient(String info){
		String[] data = info.split(":");
		ingredients.add(new Ingredient(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2])));
	}
	public void addDirection(String direction){
		directions.add(direction);
	}
	public void  setScore(int score){
		this.searchScore = score;
	}
	public int getScore(){
		return searchScore;
	}
	public void readData() throws IOException{
		FileReader in = null;
		String data = null;
		try {
			in = new FileReader(name);
			char c;
			do{
				c = (char)in.read();
				data = data + c;
			}while(c !='~');
		}finally{
			if(in != null){
				in.close();
			}
		}
		delegateData(data);
	}
	public void save() throws IOException{
		FileWriter out = null;
		Iterator<Ingredient> i = ingredients.iterator();
		Iterator<String> j = directions.iterator();
		String info = "";
		Ingredient temp;
		while(i.hasNext()){
			temp = i.next();
			info = info + temp.getName()+":"+temp.getQuantity()+":"+temp.getUnit()+"|";
		}
		info = info + "--DIRECTIONS--|";
		String direction;
		while(j.hasNext()){
			direction = j.next();
			info = info + direction +"|";
		}
		try{
			out = new FileWriter(name);
			out.write(info);
		}finally{
			if(out!=null){
				out.close();
			}
		}
	}
	@Override
	public int compareTo(Recipe arg0) {
		return Integer.compare(arg0.getScore(), searchScore);
	}
	
}
