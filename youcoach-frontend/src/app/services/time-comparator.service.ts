import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TimeComparatorService {

  constructor() { }

  private dateStringFormatMatches(input: string): boolean {
    return /[0-9]{2}\/[0-9]{2}\/[0-9]{4}/.test(input);
  }

  private timeStringFormatMatches(input: string): boolean {
    return /[0-9]{2}:[0-9]{2}/.test(input);
  }

  private parseDateStringToArray(input: string): number[] {
    return input.split('/').map(v => parseInt(v));
  }

  private parseDateStringForYear(input: string): number {
    return this.parseDateStringToArray(input)[2];
  }

  private parseDateStringForMonth(input: string): number {
    return this.parseDateStringToArray(input)[1];
  }

  private parseDateStringForDay(input: string): number {
    return this.parseDateStringToArray(input)[0];
  }

  private parseTimeStringToArray(input: string): number[] {
    return input.split(':').map(v => parseInt(v));
  }

  private parseTimeStringForHour(input: string): number {
    return this.parseTimeStringToArray(input)[0];
  }

  private parseTimeStringForMinutes(input: string): number {
    return this.parseTimeStringToArray(input)[1];
  }

  public constructSessionDate(date: string, time: string): Date | null {
    if (this.dateStringFormatMatches(date) && this.timeStringFormatMatches(time)) {
      const year = this.parseDateStringForYear(date);
      const month = this.parseDateStringForMonth(date) - 1;
      const day = this.parseDateStringForDay(date);
      const hour = this.parseTimeStringForHour(time);
      const minutes = this.parseTimeStringForMinutes(time);
      return new Date(year, month, day, hour, minutes, 0, 0);
    } else {
      return null;
    }
  }
}
