package sample.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ghost {
    Image image;
    ImageView imageView;
    DIRECTIONS direction;

    public Ghost(Image image) {
        this.image = image;
        this.imageView = new ImageView(image);
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setDirection(DIRECTIONS direction) {
        this.direction = direction;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public DIRECTIONS getDirection() {
        return direction;
    }
}
