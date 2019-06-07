package uroborosGameStudio.domain;

import java.awt.Component;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import uroborosGameStudio.domain.appModel.MainWindowModel;

public abstract class GameObject implements Serializable
{
	private static final long serialVersionUID = 1L;
	String name = "";
	String ext = "";

	public String getName()
	{
		return this.name;
	}
	
	public String getExt()
	{
		return this.ext;
	}
	
	public abstract void setName(String name);

	public void saveFile(String savedPath) throws IOException
	{
		File file = new File(savedPath + getName() + getExt());
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(this);
		oos.close();
	}

	public abstract void setSceneUEngine();

	public abstract SceneWrapper selectedScene(MainWindowModel model);

	public abstract void setPosition(Integer x, Integer y);

	public abstract Integer getX();

	public abstract Integer getY();

	public abstract String getPathImage();

	public abstract Integer getWidth();

	public abstract Integer getHeight();

	public abstract void setPathImage(String path);

	public abstract void setDimensionImage(Integer width, Integer height);

	public abstract List<BehaviorFile> getBehaviors();

	public abstract void addBehavior(BehaviorFile newBehavior);

	public abstract void removeBehaviorIndex(int fileSelected);

	public abstract String getBehaviorFileIndex(int index);

	public abstract void setBehaviorFileText(Integer file, String text);

	public abstract void setStatic();

	public abstract void setKinematic();

	public abstract void setDynatic();

	public abstract void setPhysicsBody(String body);

	public abstract String getBody();

	public abstract Physics getPhysicsType();

	public abstract List<Collider> getColliders();

	public abstract void addCollision(Collider collition);

	public abstract String getCollitionCode(int index);

	public abstract void setCollitionText(Integer index, String text);

	public abstract void removeCollisionIndex(int fileSelected);

}
