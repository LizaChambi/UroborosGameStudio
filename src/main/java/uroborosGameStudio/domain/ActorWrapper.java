package uroborosGameStudio.domain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.team.uroboros.uroboros.engine.Game;
import org.team.uroboros.uroboros.engine.component.Actor;
import org.team.uroboros.uroboros.engine.component.Scene;
import org.team.uroboros.uroboros.engine.geometry.Dimension;
import org.team.uroboros.uroboros.engine.geometry.Point;
import org.team.uroboros.uroboros.engine.ui.TextureRenderer;
import org.team.uroboros.uroboros.engine.ui.resources.Animation;
import org.team.uroboros.uroboros.engine.ui.resources.Frame;
import org.team.uroboros.uroboros.engine.ui.resources.Sprite;
import org.team.uroboros.uroboros.engine.ui.resources.SpriteSheet;
import org.team.uroboros.uroboros.engine.ui.resources.Texture;

import com.team.uroboros.jtypescript.engine.EcmaScriptEngine;

import uroborosGameStudio.domain.appModel.MainWindowModel;
import uroborosGameStudio.exception.NombreVacioException;

public class ActorWrapper extends GameObject  implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private String pathImage;
	private java.awt.Point position;
	private java.awt.Dimension dimension;
	private transient BufferedImage image;
	private double frames;
	private AdmBehaviors behaviors;
	private AdmColliders collisions;
	private Body body;
	private Physics physicType;
	private Integer ratio;

	public ActorWrapper(String name, String path, int x, int y) 
	{
		this.name = name;
		setPathImageLocal(path);
		this.position = new java.awt.Point(x, y);
		this.dimension = new java.awt.Dimension(this.getRealWidth(), this.getRealHeight());
		this.frames = 1;
		this.ext = ".act";
		this.behaviors = new AdmBehaviors();
		this.collisions = new AdmColliders();
		this.body = new Bodiless();
		this.physicType = Physics.NONE;
		this.ratio = 0;
	}

	public ActorWrapper(String name, String path, int x, int y, int width, int height, int sprites, int ratio) 
	{
		this.name = name;
		this.ext = ".act";
		setPathImageLocal(path);
		this.position = new java.awt.Point(x, y);
		this.dimension = new java.awt.Dimension(width, height);
		this.frames = sprites;
		this.behaviors = new AdmBehaviors();
		this.collisions = new AdmColliders();
		this.body = new Bodiless();
		this.physicType = Physics.NONE;
		this.ratio = ratio;
	}

	public ActorWrapper() {
		// TODO Auto-generated constructor stub
	}

	public List<BehaviorFile> getBehaviors()
	{
		return this.behaviors.getBehaviors();
	}
	
	public int getRealWidth() {
		return this.image.getWidth();
	}
	
	public java.awt.Point getPosition()
	{
		return this.position;
	}
	
	public java.awt.Dimension getDimension()
	{
		return this.dimension;
	}

	public int getRealHeight() {
		return this.image.getHeight();
	}

	private void readImage(String path) {
		try {
			this.image = ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public BufferedImage getImage() {
		return this.image;
	}

	@Override
	public Integer getX() {
		return this.position.x;
	}

	@Override
	public Integer getY() {
		return this.position.y;
	}
	
	@Override
	public Integer getWidth() {
		return this.dimension.width;
	}

	@Override
	public Integer getHeight() {
		return this.dimension.height;
	}

	@Override
	public void setName(String newName) {
		if(newName.equals("")) throw new NombreVacioException(this);
		Game.rename(Game.getActorOnCurrentScene(name), newName);
		this.name = newName;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public void setSceneUEngine() 
	{
		Scene selectedScene = Game.getSceneWithActor(this.name);
		Game.setScene(selectedScene);
	}
	
	@Override
	public SceneWrapper selectedScene(MainWindowModel model) 
	{
		return model.searchScene(Game.getSceneWithActor(this.getName()).getName());
	}

	@Override
	public void setPosition(Integer x, Integer y) 
	{
		this.position = new java.awt.Point(x,y);
		Game.getActorOnCurrentScene(name).translateTo(x,y);
	}

	public Boolean hasName(String name) 
	{
		return getName().equals(name);
	}

	@Override
	public String getPathImage() {
		return this.pathImage;
	}

	@Override
	public void setPathImage(String path) 
	{
		setPathImageLocal(path);
		setPathImageUEngine(path);
	}

	private void setPathImageLocal(String path) 
	{	
		this.pathImage = path;
		readImage(path);
	}

	private void setPathImageUEngine(String path) 
	{
		SpriteSheet spritesheet = new SpriteSheet(path, new Frame(Point.ORIGIN, new Dimension(this.getRealWidth(), this.getRealHeight())) );
		Sprite sprite = new Sprite(spritesheet, 0);
		Game.getActorOnCurrentScene(name).setTexture(sprite);
	}

	@Override
	public void setDimensionImage(Integer width, Integer height) 
	{
		this.dimension = new java.awt.Dimension(width, height);
		Game.getActorOnCurrentScene(name).setDimension(width, height);
	}
	
	public void load() 
	{
		readImage(this.pathImage);
		loadActorUEngine();
	}

	private void loadActorUEngine() 
	{	// CAMBIAR EN EL CASO DE SER UNA ANIMACIÓN O UNA IMAGEN COMUN
		
		Actor actorLoaded = Game.createActorOnCurrentScene(this.name);
		if(isAnimation())
		{
			// Cargar animacion
			List<Frame> frames = new ArrayList<Frame>();
			List<Integer> indexs = new ArrayList<Integer>();
			Dimension dimension = new Dimension(this.getWidth(), this.getHeight());
			
			Integer x = 0;
			for (int i = 0; i < this.getSprites(); i++)
			{
				frames.add(new Frame(new Point(x,0), dimension));
				indexs.add(i);
				x+=this.getWidth();
			}
			
			Frame[] objects = new Frame[frames.size()]; 
			objects = frames.toArray(objects); 
			Integer[] indexAux = new Integer[indexs.size()]; 
			indexAux = indexs.toArray(indexAux); 
			
			SpriteSheet spritesheet = new SpriteSheet(this.pathImage, objects);
			Texture sprite = new Animation(spritesheet, this.ratio, indexAux); //lista de los indices que usa la animacion. El 2do numero mientras mas grande más lento
			actorLoaded.setDimension(dimension);
			actorLoaded.setTexture(sprite);
		}
		else
		{
			SpriteSheet spritesheet = new SpriteSheet(this.pathImage, new Frame(Point.ORIGIN, new Dimension(this.getRealWidth(), this.getRealHeight())) );
			Sprite sprite= new Sprite(spritesheet, 0);
			actorLoaded.setDimension(new Dimension(this.getWidth(), this.getHeight()));
			actorLoaded.setTexture(sprite);
		}
		
		actorLoaded.learn(new TextureRenderer());
		actorLoaded.translate(new Point(this.getX(), this.getY()));
		
		this.body.setPhysicsBodyUEngine(this);
		this.setPhysicsTypeUEngine(this.physicType);
	}


	@Override
	public void addBehavior(BehaviorFile newBehavior) 
	{
		this.behaviors.addBehavior(newBehavior);
	}

	public Boolean hasBehaviorName(String name) 
	{
		return this.behaviors.hasBehaviorName(name);
	}

	@Override
	public void removeBehaviorIndex(int index) 
	{
		this.behaviors.removeBehaviorIndex(index);
	}

	@Override
	public String getBehaviorFileIndex(int index) 
	{
		return this.behaviors.getBehaviorFile(index);
	}

	@Override
	public void setBehaviorFileText(Integer file, String text) 
	{
		this.behaviors.setCodeFile(file, text);
	}

	public void evalBehaviors(EcmaScriptEngine engine) 
	{
		this.behaviors.evalBehaviorFiles(engine);
	}

	public void learnAbilities(EcmaScriptEngine engine) 
	{
		// Actor actor = Game.getActorOnCurrentScene(name);
		Actor actor = Game.getActor(name);
		this.behaviors.getBehaviors().forEach(behavior -> behavior.learnAbility(actor, engine));
	}

	public void save(String savedPath) throws IOException 
	{
		String dir = savedPath + name + line();
		createFolder(dir);
		saveFile(dir);
		this.behaviors.saveBehaviors(dir);
	}

	@Override
	public void setPhysicsType(Physics type)
	{
		this.physicType = type;
		this.setPhysicsTypeUEngine(type);
	}

	private void setPhysicsTypeUEngine(Physics type) 
	{
		Actor actor = Game.getActorOnCurrentScene(this.name);
		if (type.equals(Physics.DYNAMIC))
		{
			actor.setAsDynamic();
		}
		if (type.equals(Physics.KINEMATIC))
		{
			actor.setAsKinematic();
		}
		if (type.equals(Physics.STATIC))
		{
			actor.setAsStatic();
		}
	}

	@Override
	public void setPhysicsBody(Body body) 
	{
		this.body = body;
		this.body.setPhysicsBodyUEngine(this);
	}

	@Override
	public Body getBody() 
	{
		return this.body;
	}

	@Override
	public Physics getPhysicsType() 
	{
		return this.physicType;
	}

	@Override
	public List<Collider> getColliders() 
	{
		return this.collisions.getColliders();
	}

	@Override
	public void addCollision(Collider collition) 
	{
		this.collisions.addCollider(collition);
	}

	@Override
	public String getCollitionCode(int index) {
		return this.collisions.getCollitionIndex(index).getCode();
	}

	@Override
	public void setCollitionText(Integer index, String text) 
	{
		this.collisions.getCollitionIndex(index).setCode(text);
	}

	@Override
	public void removeCollisionIndex(int index) 
	{
		this.collisions.removeColliderIndex(index);
	}

	public void evalCollisions(EcmaScriptEngine engine) 
	{
		this.collisions.evalColliders(engine, this.name);
	}

	@Override
	public void setPathAudio(String path) {}

	@Override
	public String getPathAudio() {
		return "";
	}

	public boolean isAnimation() 
	{
		return this.frames>1;
	}

	public double getSprites() 
	{
		return this.frames;
	}

	public Integer getRatio() 
	{
		return this.ratio;
	}
	
	public void setSprites (int sprites) 
	{
		this.frames = sprites;
	}
	
	public void setRatio(int ratio)
	{
		this.ratio = ratio;
	}
	
	@Override
	public void setAnimation (String image, int sprites, Dimension newDim, int ratio) 
	{
		setPathImageLocal(image);
		setDimensionImage(newDim.getWidth().intValue(), newDim.getHeight().intValue());
		setRatio(ratio);
		setSprites(sprites);
		
		setAnimationUEngine(image, sprites, newDim, ratio);
	}

	private void setAnimationUEngine(String image, int sprites, Dimension newDim, int ratio) 
	{
		Texture sprite = generateSprite(image, sprites, newDim, ratio); 
		Game.getActorOnCurrentScene(name).setTexture(sprite);
	}

	private Texture generateSprite(String image, int sprites, Dimension newDim, int ratio) 
	{
		List<Frame> frames = new ArrayList<Frame>();
		List<Integer> indexs = new ArrayList<Integer>();
		
		Dimension dimension = new Dimension(newDim.getWidth(), newDim.getHeight());
		generateFrames(sprites, newDim, frames, indexs, dimension);
		Frame[] objects = new Frame[frames.size()]; 
		objects = frames.toArray(objects); 
		
		Integer[] indexAux = new Integer[indexs.size()]; 
		indexAux = indexs.toArray(indexAux);
		
		SpriteSheet spritesheet = new SpriteSheet(image, objects);
		return new Animation(spritesheet, ratio, indexAux);
	}

	private void generateFrames(int sprites, Dimension newDim, List<Frame> frames, List<Integer> indexs,
			Dimension dimension) {
		Integer x = 0;
		for (int i = 0; i < sprites; i++)
		{
			frames.add(new Frame(new Point(x,0), dimension));
			indexs.add(i);
			x = (int) (x + newDim.getWidth());
		}
	}

	public boolean hasCollidersOrBehaviors() 
	{
		return this.behaviors.hasBehaviors() || this.collisions.hasColliders();
	}
}
