package sample.socket;

import javafx.scene.image.Image;
import java.io.Serializable;

    public class Data implements Serializable {

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Image getImage() {
            return image;
        }

        public void setImage(Image image) {
            this.image = image;
        }

        @Override
        public String toString() {
            return name;
        }

        public byte[] getFile() {
            return file;
        }

        public void setFile(byte[] file) {
            this.file = file;
        }

        private String status;
        private Image image;
        private byte[] file;
        private String name;
    }

