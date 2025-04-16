import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'specialPipe'
})
export class SpecialPipePipe implements PipeTransform {

  transform(value: string): string {
    let newVal = value.replace(/[^\w\s]/gi, "")
    return newVal;
  }

}
