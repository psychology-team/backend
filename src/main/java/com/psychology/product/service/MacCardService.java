package com.psychology.product.service;

import com.psychology.product.repository.dto.MakCardDTO;
import com.psychology.product.repository.model.MakCardDAO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MacCardService {
    MakCardDAO createCard(List<MultipartFile> files);
}
