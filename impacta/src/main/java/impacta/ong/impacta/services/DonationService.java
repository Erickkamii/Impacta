package impacta.ong.impacta.services;

import impacta.ong.impacta.domain.donation.Donation;
import impacta.ong.impacta.domain.user.Ong;
import impacta.ong.impacta.domain.user.Volunteer;
import impacta.ong.impacta.dto.DonationRequestDTO;
import impacta.ong.impacta.dto.DonationResponseDTO;
import impacta.ong.impacta.repositories.DonationRepository;
import impacta.ong.impacta.repositories.OngRepository;
import impacta.ong.impacta.repositories.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private OngRepository ongRepository;

    public DonationResponseDTO createDonation(DonationRequestDTO donationRequest) {
        Volunteer volunteer = volunteerRepository.findById(donationRequest.volunteerId())
                .orElseThrow(() -> new RuntimeException("Volunteer not found"));

        Ong ong = ongRepository.findById(donationRequest.ongId())
                .orElseThrow(() -> new RuntimeException("Ong not found"));

        Donation donation = new Donation();
        donation.setValue(donationRequest.value());
        donation.setVolunteer(volunteer);
        donation.setOng(ong);
        donation.setDonationDate(donationRequest.donationDate());

        donationRepository.save(donation);

        return new DonationResponseDTO(
                donation.getId(),
                donation.getValue(),
                donation.getDonationDate(),
                volunteer.getUser().getName(),
                ong.getUser().getName()
        );
    }
}
