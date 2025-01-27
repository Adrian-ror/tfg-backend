package es.udc.tfg.tfgprojectbackend.model.exceptions;

/**
 * Exception thrown when the maximum number of images for a product is exceeded.
 */
@SuppressWarnings("serial")
public class MaxImagesExceededException extends Exception {

    private int maxAllowedImages;

    public MaxImagesExceededException(int maxAllowedImages) {
        this.maxAllowedImages = maxAllowedImages;
    }

    public int getMaxAllowedImages() {
        return maxAllowedImages;
    }

}
