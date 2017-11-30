package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

public class SimpleCrateGame implements Game {
	// Store context
    private Window window;
    
    // We need our physics engine
    private World world;
    
    // And we need to keep references on our game objects
    private Entity block;
    private Entity crate;
    
    // graphical representation of the body
    private ImageGraphics graphicsBlock ;
    private ImageGraphics graphicsCrate;
    private float sizeCrate=2.0f;
    private float sizeBlock=2.0f;
    
	public boolean begin(Window window, FileSystem fileSystem) {
		// Store context
        this.window = window;
        
        // Successfully initiated
        world = new World();
        world.setGravity(new Vector(0.0f, -9.81f));
        
        // To create an object , you need to use a builder
        EntityBuilder entityBuilder = world.createEntityBuilder() ;
        
        // Make sure this does not move
        entityBuilder.setFixed(true) ;
        
        // This helps you define properties , like its initial location
        entityBuilder.setPosition(new Vector(1.0f, 0.5f)) ;
        
     // Once ready , the body can be built
        block = entityBuilder.build() ;        
	     // At this point , your body is in the world , but it has no geometry
	     // You need to use another builder to add shapes
	     PartBuilder partBuilder = block.createPartBuilder() ;
	     // Create a square polygon , and set the shape of the builder to this polygon
	     Polygon polygon = new Polygon(
	     new Vector(0.0f, 0.0f),
	     new Vector(sizeBlock, 0.0f),
	     new Vector(sizeBlock, sizeBlock),
	     new Vector(0.0f, sizeBlock)
	     ) ;
	     partBuilder.setShape(polygon) ;
	     // Finally , do not forget the following line.
	     partBuilder.setFriction(0.5f) ;
	     partBuilder.build() ;
	     // Note : we do not need to keep a reference on partBuilder
        graphicsBlock = new ImageGraphics("stone.broken.4.png", sizeBlock, sizeBlock) ;        
        graphicsBlock.setParent(block) ;
        

        
        entityBuilder.setPosition(new Vector(0.2f, 4.0f)) ;
        entityBuilder.setFixed(false) ;
        crate = entityBuilder.build() ;
	     // At this point , your body is in the world , but it has no geometry
	     // You need to use another builder to add shapes
	     PartBuilder partBuilder3 = crate.createPartBuilder() ;
	     // Create a square polygon , and set the shape of the builder to this polygon
	     Polygon polygon2 = new Polygon(
	     new Vector(0.0f, 0.0f),
	     new Vector(sizeCrate, 0.0f),
	     new Vector(sizeCrate, sizeCrate),
	     new Vector(0.0f, sizeCrate)
	     ) ;
	     partBuilder3.setShape(polygon2) ;
	     // Finally , do not forget the following line.
	     partBuilder3.setFriction(0.5f) ;
	     partBuilder3.build() ;
        graphicsCrate = new ImageGraphics("box.4.png", sizeCrate, sizeCrate) ;
        graphicsCrate.setParent(crate) ;
        

		
		return true;
	}
	 // This event is called at each frame
    @Override
    public void update(float deltaTime) {
    	// Simulate physics
    	// Our body is fixed , though , nothing will move
    	world.update(deltaTime) ;
    	// We can render our scene now,
    	graphicsBlock.draw(window) ;
    	graphicsCrate.draw(window);
    	
    	// we must place the camera where we want
    	// We will look at the origin (identity) and increase the view size a bit
    	window.setRelativeTransform(Transform.I.scaled(10.0f)) ;
    }
    @Override
    public void end() {
        // Empty on purpose, no cleanup required yet
    }
}
