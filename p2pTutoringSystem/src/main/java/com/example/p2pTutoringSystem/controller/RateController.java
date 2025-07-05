package com.example.p2pTutoringSystem.controller;

import com.example.p2pTutoringSystem.dto.RatingRequest;
import com.example.p2pTutoringSystem.projections.TutorRatingProjection;
import com.example.p2pTutoringSystem.services.RateService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController()
@RequestMapping("api/v1/rates")
@AllArgsConstructor
public class RateController {

    private final RateService rateService;

    @PostMapping("/student-rate-tutor/{studentId}/{tutorId}")
    public ResponseEntity<String> studentRateService(
            @PathVariable long studentId,
            @PathVariable long tutorId,
            @RequestBody RatingRequest ratingRequest) {
        String response = rateService.studentRatesTutor(studentId, tutorId, ratingRequest);

        if(response.equals("You rate this tutor successfully!")){
            return ResponseEntity.ok(response); 
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @GetMapping("/average")
    public List<TutorRatingProjection> getAverageRatings() {
        return rateService.getAverageRatingsByTutor();
    }

}
