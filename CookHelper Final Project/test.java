import java.io.IOException;
import java.util.Iterator;

public class test {
	public static void main(String[] args){
		String recipe = "Steak:American:Meat:20Min:4";
		RecipeBook pablo = new RecipeBook("Pablo");
		pablo.addRecipe(recipe);
		Iterator<Recipe> i = pablo.getRecipes().iterator();
		Recipe recie = i.next();
		System.out.println("Name: "+recie.getName());
		try {
			pablo.save();
		} catch (IOException e) {
			System.out.println("oops");
		}
		RecipeBook newPablo = new RecipeBook("Pablo.txt");
		newPablo.loadSave();
		Iterator<Recipe> j = newPablo.getRecipes().iterator();
		Recipe recaie = j.next();
		System.out.println("Name: "+recaie.getName());
	}
}
