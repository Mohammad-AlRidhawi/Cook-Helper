
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class RecipeBook {
	private String name;
	private LinkedList<Recipe> recipes;
	public RecipeBook(String name){
		this.name = name;
		recipes = new LinkedList<Recipe>();
	}
	public void loadSave(){
		FileReader in = null;
		String data = "";
		try {
			in = new FileReader(name);
			char c;
			do{
				c = (char)in.read();
				data = data + c;
			}while(c !='~');
			in.close();
		}catch(IOException e){
			
		}
		String[] recipes = data.split("/");
		for(int i =0; i < recipes.length-1; i++){
			System.out.println(recipes[i]);
			addRecipe(recipes[i]);
		}
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedList<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(LinkedList<Recipe> recipes) {
		this.recipes = recipes;
	}

	public void addRecipe(String recipe){
		String[] recipeInfo= recipe.split(":");
		System.out.println(recipeInfo[0]);
		recipes.add(new Recipe(recipeInfo[0], recipeInfo[1], recipeInfo[2], recipeInfo[3], 3));
	}
	public void save() throws IOException{
		FileWriter out = null;
		Iterator<Recipe> i = recipes.iterator();
		String info = "";
		while(i.hasNext()){
			Recipe current = i.next();
			//current.save();//NOTE! this may change depending on the execution of the system
			info = info + current.getName() +":"+ current.getCatergory() +":"+ current.getType() +":"+ current.getCookingTime() +":"+ current.getRating()+"/";
		}
		info = info + '~';
		try{
			out = new FileWriter(name);
			out.write(info);
		}finally{
			if(out!=null){
				out.close();
			}
		}
		
	}
	
	
	public PriorityQueue<Recipe> search(String catergory, String type, String condition){
		Queue<Recipe> matches = narrowSearch(catergory, type);
		Recipe current;
		PriorityQueue<Recipe> result = new PriorityQueue<Recipe>();
		while(!matches.isEmpty()){
			current = matches.remove();
			try {
				current.readData();
			} catch (IOException e) {
				current.setScore(0);
			}
			checkIngredients(condition, current);
			result.add(current);
		}
		return result;
	}
	private void checkIngredients(String condition, Recipe current){
		String[] conditions = condition.split(" ");
		Boolean last = true;
		String operator = "NONE";
		Boolean negated = false;
		String or, and, not;
		or = "OR";
		and = "AND";
		not = "NOT";
		int score=0;
		for(int j = 0; j<conditions.length; j++){
			if(conditions[j] == not){
				//Here if someone puts two NOT's in a row then the logic comes out to just never having NOT
				if(negated){
					negated = false;
				}else{
					negated = true;
				}
			}else if(conditions[j] == and){
				operator = and;
			}else if(conditions[j] == or){
				operator = or;
			}else{
				Iterator<Ingredient> i = current.getIngredients().iterator();
				Ingredient ing;
				Boolean found = false;
				while(i.hasNext() & !found){
					ing = i.next();
					if(ing.getName() == conditions[j]){
						found=true;
					}
				}
				if(negated){
					if(found){
						found = false;
					}else{
						found = true;
					}
				}
				if(found){
					if(operator == "NONE"){
						score++;
						last = true;
					}else if (operator == and){
						if(last){
							score = score + 2;
						}else{
							score++;
						}
					}else if(operator == or){
						if(!last){
							score++;
						}
					}
				}
			}
		}
		current.setScore(score);
	}
	private Queue<Recipe> narrowSearch(String catergory, String type){
		Recipe current;
		Queue<Recipe> matches = new LinkedList<Recipe>();
		Iterator<Recipe> i = recipes.iterator();
		if(catergory == "NONE" & type == "NONE"){
			while(i.hasNext()){
				current = i.next();
				matches.add(current);
			}
		}else if(catergory =="NONE"){
			while(i.hasNext()){
				current = i.next();
				if(current.getType() == type){
					matches.add(current);
				}
			}
		}else if(type == "NONE"){
			while(i.hasNext()){
				current = i.next();
				if(current.getCatergory() == catergory){
					matches.add(current);
				}
			}
		}else{
			while(i.hasNext()){
				current = i.next();
				if(current.getCatergory() == catergory & current.getType() == type){
					matches.add(current);
				}
			}
		}
		return matches;
	}
}
