import org.junit.Test;

import static org.junit.Assert.*;

public class RockPaperScissorTest {

    RockPaperScissor rock = new RockPaperScissor();
    final String[] weaponList = {"Rock","Scissors","Paper"};

    @Test
    public void generateRequest() {
        String result = rock.generateRequest(weaponList);
        assertEquals("Please select 1 Rock 2 Scissors 3 Paper", result);
    }

    @Test
    public void determineWinner() {
        String result = rock.determineWinner(weaponList,0,0);
        assertEquals("Draw both selected Rock", result);
    }



}
