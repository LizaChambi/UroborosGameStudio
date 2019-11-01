package uroborosGameStudio.domain;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.team.uroboros.uroboros.engine.Game;
import org.team.uroboros.uroboros.engine.audio.Audio;
import org.team.uroboros.uroboros.engine.component.Actor;
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

public class SceneWrapper extends GameObject implements Serializable
{
	private static final long serialVersionUID = 1L;
	private List<ActorWrapper> actors;
	private String pathAudio;
	private org.team.uroboros.uroboros.engine.audio.Audio audio;
	
	public SceneWrapper(String name)
	{
		this.name = name;
		this.ext = ".sce";
		this.actors = new ArrayList<ActorWrapper>();
		this.pathAudio = "";
	}
	
	public String getPathAudio()
	{
		return this.pathAudio;
	}

	public List<ActorWrapper> getActors()
	{
		return this.actors;
	}
	
	public void addActor(ActorWrapper actorWpp)	{
		this.actors.add(actorWpp);
		createActorUEngine(actorWpp);
	}

	public void createActorUEngine(ActorWrapper actorWpp) 
	{
		if(actorWpp.isAnimation())
		{
			createAnimation(actorWpp);
		}
		else
		{
			createSimpleActor(actorWpp);
		}
	}

	private void createSimpleActor(ActorWrapper actorWpp) {
		Actor newActor = Game.createActorOnCurrentScene(actorWpp.getName());
		SpriteSheet spritesheet = new SpriteSheet(actorWpp.getPathImage(), new Frame(new Point(0,0), new Dimension(actorWpp.getRealWidth(), actorWpp.getRealHeight())));
		Sprite sprite = new Sprite(spritesheet, 0);
		newActor.setDimension(new Dimension(actorWpp.getWidth(), actorWpp.getHeight()));
		newActor.setTexture(sprite);
		newActor.learn(new TextureRenderer());
		newActor.translate(new Point(actorWpp.getX(), actorWpp.getY()));
	}

	private void createAnimation(ActorWrapper actorWpp) 
	{
		Actor newActor = Game.createActorOnCurrentScene(actorWpp.getName());
		List<Frame> frames = new ArrayList<Frame>();
		List<Integer> indexs = new ArrayList<Integer>();
		Dimension dimension = new Dimension(actorWpp.getWidth(), actorWpp.getHeight());
		
		generateFrames(actorWpp, frames, indexs, dimension);
		Frame[] objects = new Frame[frames.size()]; 
		objects = frames.toArray(objects); 
		Integer[] indexAux = new Integer[indexs.size()]; 
		indexAux = indexs.toArray(indexAux); 
		
		createAnimationUEngine(actorWpp, newActor, dimension, objects, indexAux);
	}

	private void generateFrames(ActorWrapper actorWpp, List<Frame> frames, List<Integer> indexs, Dimension dimension) 
	{
		Integer x = 0;
		for (int i = 0; i < actorWpp.getSprites(); i++)
		{
			frames.add(new Frame(new Point(x,0), dimension));
			indexs.add(i);
			x+=actorWpp.getWidth();
		}
	}

	private void createAnimationUEngine(ActorWrapper actorWpp, Actor newActor, Dimension dimension, Frame[] objects,Integer[] indexAux) 
	{
		SpriteSheet spritesheet = new SpriteSheet(actorWpp.getPathImage(), objects);
		Texture sprite = new Animation(spritesheet, actorWpp.getRatio(), indexAux); //lista de los indices que usa la animacion. El 2do numero mientras mas grande más lento
		newActor.setDimension(dimension);
		newActor.setTexture(sprite);
		newActor.learn(new TextureRenderer());
	}
	

	public Integer cantActors() 
	{
		return this.actors.size();
	}

	public ActorWrapper getActorIn(Integer index) 
	{
		return this.actors.get(index);
	}

	@Override
	public String toString() 
	{
		return this.name;
	}

	@Override
	public void setName(String newName) 
	{
		if(newName.equals("")) throw new NombreVacioException(this);
		Game.rename(Game.getScene(name), newName);
		this.name = newName;
	}

	public boolean hasName(String name2) 
	{
		return name == name2;
	}

	public void save(String savedPath) throws IOException
	{
		String dir = savedPath + name + line();
		createFolder(dir);
		saveFile(dir);
		saveMedia();
		saveActors(dir);
	}

	private void saveMedia() 
	{
		// TODO COPIAR LOS ARCHIVOS MULTIMEDIA CARGADOS POR EL USUARIO EN LA CARPETA DEL PROYECTO
	}

	private void saveActors(String savedPath)
	{
		this.actors.forEach(actor -> {
			try {
				actor.save(savedPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	@Override
	public void setSceneUEngine() 
	{
		Game.setScene(this.name);
	}

	@Override
	public SceneWrapper selectedScene(MainWindowModel model) 
	{
		return this;
	}

	public Boolean existActorName(String name) 
	{
		return this.actors.stream().anyMatch(actor -> actor.hasName(name));
	}
	
	public void load() 
	{
		Game.createScene(this.name);
		Game.setScene(this.name);
		this.actors.forEach(actor -> actor.load());
	}
	
	public void deleteActor(String name) {
		this.actors.removeIf(actor -> actor.hasName(name));
		Game.removeActor(name);
	}

	public Boolean existBehaviorName(String name) 
	{
		return this.actors.stream().anyMatch(actor -> actor.hasBehaviorName(name));
	}

	public void evalBehaviors(EcmaScriptEngine engine) 
	{
		this.actors.forEach(actor -> actor.evalBehaviors(engine));
	}

	public void actorsLearnAbilities(EcmaScriptEngine engine) 
	{
		this.actors.forEach(actor -> actor.learnAbilities(engine));
	}

	public void evalCollisions(EcmaScriptEngine engine) 
	{
		this.actors.forEach(actor -> actor.evalCollisions(engine));
	}

	@Override
	public void setPathAudio(String path) 
	{
		this.pathAudio = path;
	}

	public void playAudio() 
	{
		if(! pathAudio.isEmpty())
		{
			this.audio = new Audio(pathAudio);
			audio.loop();
		}
	}

	public void stopAudio() 
	{
		if(! pathAudio.isEmpty())
		{
			audio.stop();
		}
	}

	public boolean hasBehaviorsOrCollitions() 
	{
		return this.actors.stream().anyMatch(actor -> actor.hasCollidersOrBehaviors());
	}

	public List<String> pathsMedia() 
	{
		List<String> pathsMedia = new ArrayList<String> ();
		if (! pathAudio.isEmpty())
		{
			pathsMedia.add(pathAudio);
		}
		this.actors.forEach(actor -> pathsMedia.add(actor.getPathImage()));
		return pathsMedia;
	}
}
