import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'diff'
})

export class DiffPipe implements PipeTransform {
  transform(startingDate: Date): any {
    var startDate = new Date(startingDate);
    let endDate = new Date();
    // chack start date and end date and base on condication alter date.
    if (startDate > endDate) {
      var swap = startDate;
      startDate = endDate;
      endDate = swap;
    }
    // This is for leap year.
    const startYear = startDate.getFullYear();
    const february =
      (startYear % 4 === 0 && startYear % 100 !== 0) || startYear % 400 === 0
        ? 29
        : 28;
    const daysInMonth = [31, february, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

    let yearDiff = endDate.getFullYear() - startYear;
    let monthDiff = endDate.getMonth() - startDate.getMonth();
    if (monthDiff < 0) {
      yearDiff--;
      monthDiff += 12;
    }
    let dayDiff = endDate.getDate() - startDate.getDate();
    let hourDiff = endDate.getHours() - startDate.getHours();
    let minuteDiff = endDate.getMinutes() - startDate.getMinutes();
    let secondDiff = endDate.getSeconds() - startDate.getSeconds();
    if (dayDiff < 0) {
      if (monthDiff > 0) {
        monthDiff--;
      } else {
        yearDiff--;
        monthDiff = 11;
      }
      dayDiff += daysInMonth[startDate.getMonth()];
    }

    return (
      (yearDiff > 0 ? yearDiff +
        " Years" :
        monthDiff > 0 ? monthDiff +
          " Monts" :
          dayDiff > 0 ? dayDiff +
            " Days" :
            hourDiff > 0 ? hourDiff +
              " Hours" :
              minuteDiff > 0 ? minuteDiff +
                " Minutes" :
                secondDiff > 0 ? secondDiff +
                  " Seconds" : "")
    );
  }

}
