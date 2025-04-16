import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'parseDate'
})
export class ParseDatePipe implements PipeTransform {
  transform(dateStr: string): string | null {
    if (!dateStr) return null;

    const months: { [key: string]: number } = {
      'Jan': 0, 'JAN': 0, 'Feb': 1, 'FEB': 1, 'Mar': 2, 'MAR': 2, 'Apr': 3, 'APR': 3, 'May': 4, 'MAY': 4, 'Jun': 5, 'JUN': 5,
      'Jul': 6, 'JUL': 6, 'Aug': 7, 'AUG': 7, 'Sep': 8, 'SEP': 8, 'Oct': 9, 'OCT': 9, 'Nov': 10, 'NOV': 10, 'Dec': 11, 'DEC': 11
    };

    const parts = dateStr.split('-'); // ['01', 'Jan', '2025']
    if (parts.length !== 3) return null;

    const day = parseInt(parts[0]);
    const month = months[parts[1]];
    const year = parseInt(parts[2]);


    console.log(day, month, year)
    if (isNaN(day) || isNaN(month) || isNaN(year)) return null;

    // year = year < 100 ? (year < 50 ? 2000 + year : 1900 + year) : year;
    const formattedDate = new Date(year, month, day).toISOString().split('T')[0]; // Format as YYYY-MM-DD
    return formattedDate;
  }
}
