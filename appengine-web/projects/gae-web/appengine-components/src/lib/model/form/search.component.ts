import { AbstractSubscriptionComponent } from '../../shared/subscription';
import { Directive, AfterContentInit, Inject, Host, Input } from '@angular/core';
import { GaeKeyQuerySourceFilterDirective } from '../resource/search.component';
import { combineLatest, BehaviorSubject } from 'rxjs';
import { ModelFormComponent } from '../../form/model.component';
import { filter, mergeMap, map } from 'rxjs/operators';
import { SearchParameters } from '@gae-web/appengine-api';

export type GaeKeyQuerySourceFormFilterDirectiveFunction = (model: any) => SearchParameters;

@Directive({
  selector: '[gaeKeyQuerySourceFormFilter]',
  exportAs: 'gaeKeyQuerySourceFormFilter'
})
export class GaeKeyQuerySourceFormFilterDirective extends AbstractSubscriptionComponent implements AfterContentInit {

  private _form = new BehaviorSubject<ModelFormComponent<SearchParameters>>(undefined);
  private _makeFilter = new BehaviorSubject<GaeKeyQuerySourceFormFilterDirectiveFunction>(undefined);

  constructor(@Inject(GaeKeyQuerySourceFilterDirective) @Host() public readonly filterDirective: GaeKeyQuerySourceFilterDirective<any>) {
    super();
  }

  ngAfterContentInit() {
    this.sub = this._form.pipe(
      filter((x) => Boolean(x)),
      mergeMap((x) => {
        const modelObs = x.stream.pipe(
          filter((y) => y.isComplete),
          map(() => x.model)
        );

        return combineLatest([modelObs, this._makeFilter]).pipe(
          map(([model, makeFn]) => {
            let newFilter = model;

            if (makeFn) {
              newFilter = makeFn(model);
            }

            return newFilter;
          })
        );
      })
    ).subscribe({
      next: (newFilter) => {
        this._updateFilters(newFilter);
      }
    });
  }

  public get form() {
    return this._form.value;
  }

  @Input('gaeKeyQuerySourceFormFilter')
  public set form(form: ModelFormComponent<any>) {
    this._form.next(form);
  }

  public get makeFilter() {
    return this._makeFilter.value;
  }

  @Input()
  public set makeFilter(makeFilterFn: GaeKeyQuerySourceFormFilterDirectiveFunction) {
    if (this.makeFilter !== makeFilterFn) {
      this._makeFilter.next(makeFilterFn);
    }
  }

  // MARK: Internal
  protected _updateFilters(newFilters) {
    this.filterDirective.filters = newFilters;
  }

}
