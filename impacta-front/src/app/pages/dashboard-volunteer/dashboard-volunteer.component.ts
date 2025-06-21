import { Component } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DashboardLayoutComponent } from "../../components/dashboard-layout/dashboard-layout.component";

interface Event {
  id: number;
  description: string;
  city: string;
  state: string;
  period?: string;
  status?: string;
}

interface Ong {
  id: number;
  nome: string;
  description?: string;
}

interface Skill {
  skillName: string;
  volunteerId: string;
  description: string;
  level: string;
}

@Component({
  selector: 'app-dashboard-voluntario',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule, DashboardLayoutComponent],
  templateUrl: './dashboard-volunteer.component.html',
  styleUrls: ['./dashboard-volunteer.component.scss']
})
export class DashboardVolunteerComponent {
  events: Event[] = [];
  ongs: Ong[] = [];
  loading = { skill: false };
  skill: Skill = {
    skillName: '',
    volunteerId: '',
    description: '',
    level: ''
  };
  private readonly API_URL = 'http://localhost:9090';

  constructor(private http: HttpClient) {
    this.loadEvents();
    this.loadOngs();
  }

  loadEvents() {
    this.http.get<Event[]>(`${this.API_URL}/events`).subscribe({
      next: (res) => this.events = res,
      error: () => this.events = []
    });
  }

  loadOngs() {
    this.http.get<Ong[]>(`${this.API_URL}/ong`).subscribe({
      next: (res) => this.ongs = res,
      error: () => this.ongs = []
    });
  }

  inscrever(eventId: number) {
    // Implemente a inscrição conforme sua API
    alert(`Inscrito no evento ${eventId}!`);
  }

  cadastrarSkill() {
    if (!this.skill.skillName || !this.skill.description) {
      alert('Preencha todos os campos obrigatórios');
      return;
    }
    this.loading.skill = true;
    this.http.post(`${this.API_URL}/volunteer_skill`, this.skill).subscribe({
      next: () => {
        alert('Habilidade cadastrada!');
        this.skill = { skillName: '', volunteerId: '', description: '', level: '' };
        this.loading.skill = false;
      },
      error: () => {
        alert('Erro ao cadastrar habilidade');
        this.loading.skill = false;
      }
    });
  }
}
