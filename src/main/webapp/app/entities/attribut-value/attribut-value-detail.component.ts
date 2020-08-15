import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAttributValue } from 'app/shared/model/attribut-value.model';

@Component({
  selector: 'jhi-attribut-value-detail',
  templateUrl: './attribut-value-detail.component.html',
})
export class AttributValueDetailComponent implements OnInit {
  attributValue: IAttributValue | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attributValue }) => (this.attributValue = attributValue));
  }

  previousState(): void {
    window.history.back();
  }
}
