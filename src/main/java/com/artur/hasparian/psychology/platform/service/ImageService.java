package com.artur.hasparian.psychology.platform.service;

import com.artur.hasparian.psychology.platform.repository.model.Image;

public interface ImageService {
    /**
     * Creates an Image entity from a link.
     *
     * @param imageLink The String representing the image link.
     * @return The created Image entity.
     */
    Image createImage(String imageLink);

}
