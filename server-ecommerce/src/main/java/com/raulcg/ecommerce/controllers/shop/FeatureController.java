package com.raulcg.ecommerce.controllers.shop;

import com.raulcg.ecommerce.models.Feature;
import com.raulcg.ecommerce.request.FeatureImageRequest;
import com.raulcg.ecommerce.responses.GenericApiResponse;
import com.raulcg.ecommerce.services.feature.FeatureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/common/feature")
public class FeatureController {

    private final FeatureService featureService;

    public FeatureController(FeatureService featureService) {
        this.featureService = featureService;
    }

    @PostMapping("/add")
    public ResponseEntity<GenericApiResponse<Feature>> addFeatureImage(@Validated @RequestBody FeatureImageRequest request) {
        Feature feature = featureService.addFeatureImage(request.getImage());
        GenericApiResponse<Feature> response = new GenericApiResponse<>(true, feature, "Feature added");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<GenericApiResponse<List<Feature>>> getFeatures() {
        List<Feature> features = featureService.getFeatures();
        GenericApiResponse<List<Feature>> response = new GenericApiResponse<>(true, features);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
