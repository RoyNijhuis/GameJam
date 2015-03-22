package nl.royenedwin.gamejam;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Field implements Drawable, Updateable{

	private static StaticObject[][] fields;
	private ArrayList<Collidable> collidingObjects;
	private ArrayList<Updateable> updateableObjects;
	
	public Field(int level) {
		Texture level1 = null;
		try{
			level1 = new Texture("level"+level+".png");
		} catch (Exception e){
			try {
				this.finalize();
			} catch (Throwable e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("hi");
			Game.kill();
		}
		if(level1 != null){
			collidingObjects = new ArrayList<Collidable>();
			updateableObjects = new ArrayList<Updateable>();
			fields = new StaticObject[level1.getWidth()][level1.getHeight()];
			level1.getTextureData().prepare();
			Pixmap tmp = level1.getTextureData().consumePixmap();
			for(int x=0;x<60;x++) {
				for(int y=0;y<34;y++) {
					Color color = new Color(tmp.getPixel(x, y));
					System.out.println(color.r + " " + color.g + " " + color.b);
					if(tmp.getPixel(x, y) == 255) {
						fields[x][y] = new Block();
					} else if(color.r==0.011764706f && color.g==0.5529412f && color.b == 0f) {
						//Dark green => deur 1
						fields[x][y] = new Door(new DoorKey(1));
						collidingObjects.add((Collidable) fields[x][y]);
						fields[x][y-1] = new FullNotWalk();
					} else if(color.r==0.023529412f && color.g==1f && color.b == 0f) {
						//Light green => chest 1
						fields[x][y] = new Chest(new DoorKey(1));
						collidingObjects.add((Collidable) fields[x][y]);
						fields[x+1][y] = new FullWalk();
					} else if(color.r==0.654902f && color.g==0f && color.b == 0f) {
						//Dark red => deur 2
						fields[x][y] = new Door(new DoorKey(2));
						collidingObjects.add((Collidable) fields[x][y]);
						fields[x][y-1] = new FullNotWalk();
						
					} else if(color.r==1f && color.g==0f && color.b == 0f) {
						//Light red => chest 2
						fields[x][y] = new Chest(new DoorKey(2));
						collidingObjects.add((Collidable) fields[x][y]);
						fields[x+1][y] = new FullWalk();
						
					} else if(color.r==0f && color.g==0.21176471f && color.b == 1f) {
						//Light blue => chest 3
						fields[x][y] = new Chest(new DoorKey(3));
						collidingObjects.add((Collidable) fields[x][y]);
						fields[x+1][y] = new FullWalk();
						
					} else if(color.r==0f && color.g==0.09019608f && color.b == 0.41960785f) {
						//Dark blue => deur 3
						fields[x][y] = new Door(new DoorKey(3));
						collidingObjects.add((Collidable) fields[x][y]);
						fields[x][y-1] = new FullNotWalk();
						
					} else if(color.r==0.9411765f && color.g==1f && color.b == 0f) {
						//Light yellow => chest 4
						fields[x][y] = new Chest(new DoorKey(4));
						collidingObjects.add((Collidable) fields[x][y]);
						fields[x+1][y] = new FullWalk();
						
					} else if(color.r==0.49803922f && color.g==0.5294118f && color.b == 0f) {
						//Dark yellow => deur 4
						fields[x][y] = new Door(new DoorKey(4));
						collidingObjects.add((Collidable) fields[x][y]);
						fields[x][y-1] = new FullNotWalk();
						
					}  else if(color.r==0f && color.g==1f && color.b == 0.84705883f) {
						//Light blue => trampoline
						fields[x][y] = new Trampoline();
						collidingObjects.add((Collidable) fields[x][y]);
					}  else if(color.r==1f && color.g==0.5647059f && color.b==0f) {
						//Orange => portal1
						fields[x][y] = new Portal(1);
						fields[x][y-1] = new FullWalk();
						collidingObjects.add((Collidable) fields[x][y]);
					}  else if(color.r==1f && color.g==0f && color.b == 0.9411765f) {
						//Pink => finish
						fields[x][y] = new Finish();
						collidingObjects.add((Collidable) fields[x][y]);
						fields[x+1][y] = new FullWalk();
						fields[x][y-1] = new FullWalk();
						fields[x+1][y-1] = new FullWalk();
					}  else if(color.r==0.39215687f && color.g==0.21960784f && color.b == 0f) {
						//Brown => autoturret
						fields[x][y] = new AutoTurret(false);
						collidingObjects.add((Collidable) fields[x][y]);
						updateableObjects.add((Updateable)fields[x][y]);
					} else if(color.r==0.6392157f && color.g==0.28627452f && color.b == 0.6431373f) {
						//purple => random chest
						fields[x][y] = new Chest(new HeartItem());
						collidingObjects.add((Collidable) fields[x][y]);
						fields[x+1][y] = new FullWalk();
						
					}  else if(color.r==0.14117648f && color.g==0.05882353f && color.b == 0.22352941f) {
						//dark purple => rocket launcher
						fields[x][y] = new RocketLauncher();
						collidingObjects.add((Collidable) fields[x][y]);
						updateableObjects.add((Updateable) fields[x][y]);
						fields[x+1][y] = new FullWalk();
						fields[x+2][y] = new FullWalk();
						fields[x][y+1] = new FullWalk();
						fields[x+1][y+1] = new FullWalk();
						fields[x+2][y+1] = new FullWalk();
					}
						else {
						if(!(fields[x][y] instanceof FullWalk) && !(fields[x][y] instanceof FullNotWalk)) {
							fields[x][y] = null;
						}
					}
				}
			}
		}
		
		//set positions
		for(int x=0;x<60;x++) {
			for(int y=0;y<34;y++) {
				if(fields[x][y] != null) {
					fields[x][y].setPosition(new Vector2(x*32,(33-y)*32));
				}
			}
		}
	}
	
	public ArrayList<Collidable> getCollidingObjects() {
		return collidingObjects;
	}
	
	public StaticObject[][] readMiniMap() {
		return null;
	}
	
	public static StaticObject[][] getFields() {
		return fields;
	}
	
	public static void setField(Vector2 pos, StaticObject object) {
		Field.fields[(int)pos.x][(int)pos.y] = object;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		for(StaticObject[] temp: fields) {
			for(StaticObject o: temp) {
				//Render every object in the field
				if(o != null) {
					o.render(batch);
				}
			}
		}
	}
	
	public boolean fieldHasTrampoline(int x, int y) {
		boolean result = false;
		StaticObject o = fields[x][33-y];
		
		if(o != null) {
			if(o instanceof Trampoline) {
				result = true;
			}
		}
		
		return result;
	}
	
	public static boolean fieldIsEmpty(int x, int y) {
		
		StaticObject o = fields[x][33-y];
		return o == null;
	}
	
	public boolean fieldIsFull(int x, int y) {
		
		boolean result = false;
		StaticObject o = fields[x][33-y];
		
		if(o != null) {
			if(o instanceof Door && !((Door)o).isOpen()) {
				result = true;
			}
			if(o instanceof FullNotWalk) {
				result = true;
			}
			if(o instanceof Block) {
				result = true;
			}
			if(o instanceof Trampoline) {
				result = true;
			}
		}
		
		return result;
	}
	
	public static boolean fieldIsFull2(int x, int y) {
		
		boolean result = false;
		StaticObject o = fields[x][33-y];
		
		if(o != null) {
			if(o instanceof Door && !((Door)o).isOpen()) {
				result = true;
			}
			if(o instanceof FullNotWalk) {
				result = true;
			}
			if(o instanceof Block) {
				result = true;
			}
		}
		
		return result;
	}

	@Override
	public void update(float delta) {
		for(Updateable u: updateableObjects){
			u.update(delta);
		}
	}

	@Override
	public Vector2 getPosition() {
		// TODO Auto-generated method stub
		return null;
	}
}
