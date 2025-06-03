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
    public ResponseEntity<List<DonationResponseDTO>> getAllDonations() {
        List<Donation> donations = donationRepository.findAll();

        List<DonationResponseDTO> response = donations.stream()
                .map(donation -> new DonationResponseDTO(
                        donation.getId(),
                        donation.getValue(),
                        donation.getDonationDate(),
                        donation.getVolunteer().getUser().getName(),
                        donation.getOng().getUser().getName()
                ))
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-volunteer")
    public ResponseEntity<List<DonationResponseDTO>> getAllDonationsByVolunteerName(@RequestParam String volunteerName) {
        List<Donation> donations = donationRepository.findByVolunteerUserNameIgnoreCase(volunteerName);

        List<DonationResponseDTO> response = donations.stream()
                .map(d -> new DonationResponseDTO(
                        d.getId(),
                        d.getValue(),
                        d.getDonationDate(),
                        d.getVolunteer().getUser().getName(),
                        d.getOng().getUser().getName()
                ))
                .toList();
        return ResponseEntity.ok(response);
    }
}
