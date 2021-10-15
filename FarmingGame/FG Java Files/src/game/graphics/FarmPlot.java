package game.graphics;

import game.util.Constants;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Random;

public class FarmPlot extends ImageView implements Constants {
    private ImageView soil;
    private String cropType; //stage
    private int waterLevel;
    private int maxWaterLevel = 2;
    private int minWaterLevel = -2;
    private boolean canHarvest;
    private boolean harvMax;
    private boolean canPlantSeeds;
    private boolean justPlanted;
    private boolean newSeedNewDay;
    private int gameDayCount;
    private Text txCropType;
    private int selection;
    private int daysAlive;
    private String[] stages;

    private String lvl;

    public FarmPlot(String lvl) {
        this.lvl = lvl;

        soil = new ImageView("game/res/Soil.jpg");
        this.setImage(soil.getImage());
        setFitWidth(95);
        setFitHeight(95);
        setX(0);
        setY(0);

        gameDayCount = 0;
        newSeedNewDay = false;
        harvMax = false;

        cropType = getRandStage();
        txCropType = new Text(cropType);
        txCropType.setTextAlignment(TextAlignment.CENTER);
        txCropType.setFill(Color.WHITE);
        txCropType.setFont(Font.font("verdana", FontWeight.BOLD, 13));

        updateCanPlantSeeds();
        updateCanHarvest();
    }


    public ImageView imageCrop() {
        ImageView imageC;
        if (cropType.equals(stages[1]) || cropType.equals(stages[4])
                || cropType.equals(stages[7])) { //corn
            imageC = new ImageView("game/res/corn.png");
        } else if (cropType.equals(stages[0]) || cropType.equals(stages[3])
                || cropType.equals(stages[6])) { //wheat
            imageC = new ImageView("game/res/wheat.png");
        } else if (cropType.equals(stages[2]) || cropType.equals(stages[5])
                || cropType.equals(stages[8])) { //potato
            imageC = new ImageView("game/res/potato.png");
        } else { //dead plant
            imageC = new ImageView("game/res/deadPlant.png");
        }
        imageC.setFitHeight(50);
        imageC.setFitWidth(80);
        imageC.setX(this.getX() + 9);
        imageC.setY(this.getY() + (this.getFitHeight() / 2) - 6);
        return imageC;
    }

    private String getRandStage() {
        String[] stages = {"Wheat" + System.lineSeparator() + "Seeds", "Corn"
                + System.lineSeparator() + "Seeds", "Potato"
                + System.lineSeparator() + "Seeds", "Immature" + System.lineSeparator()
                + "Wheat Plant", "Immature" + System.lineSeparator() + "Corn Plant", "Immature"
                + System.lineSeparator() + "Potato Plant", "Mature" + System.lineSeparator()
                + "Wheat Plant", "Mature" + System.lineSeparator() + "Corn Plant", "Mature"
                + System.lineSeparator() + "Potato Plant"};

        this.stages = stages;
        Random rand = new Random();
        selection = rand.nextInt(9);
        return stages[selection];
    }

    public Text rtCenterText() {
        if (selection == 9) {
            txCropType.setX(this.getX() + 7.5);
            txCropType.setY(this.getY() + (this.getFitHeight() / 2) - 17);
            txCropType.setTextAlignment(TextAlignment.CENTER);
        } else if (selection < 3) {
            txCropType.setX(this.getX() + 23);
            txCropType.setY(this.getY() + (this.getFitHeight() / 2) - 22);
            txCropType.setTextAlignment(TextAlignment.CENTER);
        } else if (selection == 4 || selection == 7) {
            txCropType.setX(this.getX() + 8);
            txCropType.setY(this.getY() + (this.getFitHeight() / 2) - 22);
            txCropType.setTextAlignment(TextAlignment.CENTER);
        } else {
            txCropType.setX(this.getX() + 2);
            txCropType.setY(this.getY() + (this.getFitHeight() / 2) - 22);
            txCropType.setTextAlignment(TextAlignment.CENTER);
        }
        return txCropType;
    }

    public void centerText(int choose) {
        if (choose == 9) {
            txCropType.setX(this.getX() + 7.5);
            txCropType.setY(this.getY() + (this.getFitHeight() / 2) - 17);
            txCropType.setTextAlignment(TextAlignment.CENTER);
        } else if (choose < 3) {
            txCropType.setX(this.getX() + 23);
            txCropType.setY(this.getY() + (this.getFitHeight() / 2) - 22);
            txCropType.setTextAlignment(TextAlignment.CENTER);
        } else if (choose == 4 || choose == 7) {
            txCropType.setX(this.getX() + 8);
            txCropType.setY(this.getY() + (this.getFitHeight() / 2) - 22);
            txCropType.setTextAlignment(TextAlignment.CENTER);
        } else {
            txCropType.setX(this.getX() + 2);
            txCropType.setY(this.getY() + (this.getFitHeight() / 2) - 22);
            txCropType.setTextAlignment(TextAlignment.CENTER);
        }
    }

    public void setDaysAlive(int num) {
        if (daysAlive == num) {
            updateCanPlantSeeds();
            updateCanHarvest();
            return;
        } else if (justPlanted) {
            daysAlive = 0;
            waterLevel = 0;
        } else {
            daysAlive = num;
            if (daysAlive == 2) {
                waterLevel--;
                if (selection + 3 > 8 || waterLevel < minWaterLevel || waterLevel > maxWaterLevel) {
                    cropType = "Dead Plant";
                    txCropType.setText(cropType);
                    selection = 9;
                } else {
                    selection += 3;
                    cropType = stages[selection];
                    txCropType.setText(cropType);
                }
            }
            if (daysAlive == 4) {
                waterLevel -= 2;
                if (selection + 3 > 8  || waterLevel < minWaterLevel
                        || waterLevel > maxWaterLevel) {
                    cropType = "Dead Plant";
                    txCropType.setText("Dead Plant");
                    selection = 9;
                } else {
                    selection += 3;
                    cropType = stages[selection];
                    txCropType.setText(cropType);
                }
            }
            if (daysAlive > 10  || waterLevel < minWaterLevel || waterLevel > maxWaterLevel) {
                cropType = "Dead Plant";
                txCropType.setText("Dead Plant");
                selection = 9;
            }
        }
        updateCanPlantSeeds();
        updateCanHarvest();
    }

    public void updateCanHarvest() {
        if (selection >= 6 && selection < 9) {
            canHarvest = true;
        } else {
            canHarvest = false;
        }
    }

    public void updateCanPlantSeeds() {
        if (selection == 9) {
            canPlantSeeds = true;
        } else {
            canPlantSeeds = false;
        }
    }

    private void updateText() {
        txCropType.setText(cropType);
    }

    public void updateCrop() {
        cropType = stages[selection];
        updateText();
    }


    public Rectangle2D getBounds() {
        return new Rectangle2D(getX() + 12, getY() + 17, getFitWidth() + 4, getFitHeight() - 4);
    }

    public boolean isIntersects() {
        return this.getBounds().intersects(PLAYER.getBounds());
    }

    //============================================================================================
    public ImageView getIV() {
        return soil;
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
        this.updateText();
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    public boolean canHarvest() {
        return canHarvest;
    }

    public void canHarvest(boolean canHarvest) {
        this.canHarvest = canHarvest;
    }

    public boolean canPlantSeeds() {
        return canPlantSeeds;
    }

    public void setCanPlantSeeds(boolean canPlantSeeds) {
        this.canPlantSeeds = canPlantSeeds;
    }

    public Text getText() {
        return txCropType;
    }

    public int getDaysAlive() {
        return daysAlive;
    }
    public boolean isJustPlanted() {
        return justPlanted;
    }

    public void setJustPlanted(boolean justPlanted) {
        this.justPlanted = justPlanted;
    }

    public void setSelectionInt(int selection) {
        this.selection = selection;
    }

    public int getGameDayCount() {
        return gameDayCount;
    }

    public void setGameDayCount(int gameDayCount) {
        this.gameDayCount = gameDayCount;
    }
    public boolean isNewSeedNewDay() {
        return newSeedNewDay;
    }

    public void setNewSeedNewDay(boolean newSeedNewDay) {
        this.newSeedNewDay = newSeedNewDay;
    }
    public boolean isHarvMax() {
        return harvMax;
    }

    public void setHarvMax(boolean harvMax) {
        this.harvMax = harvMax;
    }

    public String[] getStages() {
        return stages;
    }

    public String getLvl() {
        return lvl;
    }

}
