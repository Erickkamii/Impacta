import { Component } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DashboardLayoutComponent } from "../../components/dashboard-layout/dashboard-layout.component";

interface EventData {
ongId: any;
volunteerId: any;
  city: string;
  state: string;
  description: string;
  period: string;
  status: string;
}

@Component({
  selector: 'app-dashboard-ong',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule, DashboardLayoutComponent],
  templateUrl: './dashboard-ong.component.html',
  styleUrls: ['./dashboard-ong.component.scss']
})
export class DashboardOngComponent {
  eventData: EventData = {
    city: '',
    state: '',
    description: '',
    period: '',
    status: '',
    ongId: undefined,
    volunteerId: undefined
  };
  events: EventData[] = [];
  totalDoacoes: number = 0;
  loading = false;
  private readonly API_URL = 'http://localhost:9090';
  arrecadacaoTotal!: string | number;

  constructor(private http: HttpClient) {
    this.loadEvents();
    this.loadDoacoes();
  }

  createEvent() {
    this.loading = true;
    this.http.post(`${this.API_URL}/events`, this.eventData).subscribe({
      next: () => {
        this.loading = false;
        this.loadEvents();
        this.resetForm();
      },
      error: () => { this.loading = false; }
    });
  }

  loadEvents() {
    this.http.get<EventData[]>(`${this.API_URL}/events`).subscribe({
      next: (res) => this.events = res,
      error: () => this.events = []
    });
  }

  loadDoacoes() {
    this.http.get<{ total: number }>(`${this.API_URL}/donations/total`).subscribe({
      next: (res) => this.totalDoacoes = res.total,
      error: () => this.totalDoacoes = 0
    });
  }

  resetForm() {
    this.eventData = {
      city: '',
      state: '',
      description: '',
      period: '',
      status: '',
      ongId: undefined,
      volunteerId: undefined
    };
  }
}
