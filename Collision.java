public class Collision {
    
    public static boolean checkCollision(Entity a, Entity b){ 
        
        double dx = a.x - b.x;
        double dy = a.x - b.x;
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < (a.radius + b.radius) * 0.8) 
        {
            return true;
        }

        return false;
			
    }
}

