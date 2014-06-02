package level;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import entities.Entity;
import tiles.*;
import gfx.*;

public class Level {

	private byte[] tiles; //represents the id of the tile
	private String imagePath;
	private BufferedImage image;
	public int width;
	public int height;
	public List<Entity> entities = new ArrayList<Entity>();

	//////////////////////////////////////////////////////
	// Loads level using data from a level file
	/////////////////////////////////////////////////////
	public Level(String imagePath) {
		if(imagePath != null){
			this.imagePath = imagePath;
			this.loadLevelFromFile();
		}
		else{
		this.width = width;
		this.height = height;
		tiles = new byte[width * height];
		this.generateLevel();
		}
	}
	
	//backup loading
	private void loadLevelFromFile(){
		try{
			this.image = ImageIO.read(Level.class.getResource(this.imagePath));
			this.width = image.getWidth();
			this.height = image.getHeight();
			tiles = new byte[width * height];
			this.loadTiles();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private void loadTiles(){
		int[] tileColors = this.image.getRGB(0, 0, width, height, null, 0, width);
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
			  tileCheck: for(Tile t: Tile.tiles){
					if (t != null  && t.getLevelColor() == tileColors[x + y * width]){
						this.tiles[x + y * width] = t.getId();
						break tileCheck; //breaks out of loop
					}
				}
			}
		}
	}
	
	//not used in our project
	public void saveLevelToFile(){
		try{
			ImageIO.write(image, "png", new File(Level.class.getResource(this.imagePath).getFile()));
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void alterTile(int x, int y, Tile newTile){
		this.tiles[x + y * width] = newTile.getId();
		image.setRGB(x, y, newTile.getLevelColor());
	}
	
	public void generateLevel() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
					  tiles[x + y * width] = Tile.FLOOR.getId(); 
			} 
		}
	}

	//updates all the entities in the level
	public void tick() {
		for (Entity e : entities) {
			e.tick();
		}
	}

	//makes sure only tiles in window render
	public void renderTiles(Screen screen, int xOffset, int yOffset) {
		if (xOffset < 0)
			xOffset = 0;
		if (xOffset > ((width << 3) - screen.width))
			xOffset = ((width << 3) - screen.width);
		if (yOffset < 0)
			yOffset = 0;
		if (yOffset > ((height << 3) - screen.height))
			yOffset = ((height << 3) - screen.height);

		screen.setOffset(xOffset, yOffset);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				getTile(x, y).render(screen, this, x << 3, y << 3);
			}
		}
	}

	//renders all the entities onto the screen
	public void renderEntities(Screen screen) {
		for (Entity e : entities) {
			e.render(screen);
		}
	}

	//returns information of a selected tile
	public Tile getTile(int x, int y) {
		if (0 > x || x >= width || 0 > y || y >= height)
			return Tile.VOID;
		return Tile.tiles[tiles[x + y * width]];
	}

	//adds an entity into the level
	public void addEntity(Entity entity) {
		this.entities.add(entity);
	}
}
