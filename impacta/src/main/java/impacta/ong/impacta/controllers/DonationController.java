package impacta.ong.impacta.controllers;

import impacta.ong.impacta.domain.donation.Donation;
import impacta.ong.impacta.dto.DonationRequestDTO;
import impacta.ong.impacta.dto.DonationResponseDTO;
import impacta.ong.impacta.repositories.DonationRepository;
import impacta.ong.impacta.services.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/donations")
@RequiredArgsConstructor
public class DonationController {
    private final DonationRepository donationRepository;
    @Autowired
    private DonationService donationService;

    @PostMapping
    public ResponseEntity<DonationResponseDTO> createDonation(@RequestBody DonationRequestDTO request) {
        DonationResponseDTO response = donationService.createDonation(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Donation>> getAllDonations() {
        return ResponseEntity.ok(donationRepository.findAll());
    }
}
