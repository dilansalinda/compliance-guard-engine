import { Component, OnInit,Output, EventEmitter} from '@angular/core';
import { WishItem } from 'src/shared/models/wishitem';

const filters = [
  (item : WishItem) => item,
  (item : WishItem) => !item.isComplete,
  (item : WishItem) => item.isComplete
]

@Component({
  selector: 'filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.scss']
})
export class FilterComponent implements OnInit{

  ngOnInit(): void {
  }

  listFilter : any = '0';

  @Output() filter = new EventEmitter<any>();

  changeFilter(value : any) {
    this.filter.emit(filters[value]);
  }

}
