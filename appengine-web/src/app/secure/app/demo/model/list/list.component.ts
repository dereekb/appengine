import { Component, ViewChild, ViewEncapsulation } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { FooCreateDialogComponent } from 'src/app/secure/shared/components/model/foo/views/create-modal.component';
import { GaeListViewKeySearchSourceDirective } from '@gae-web/appengine-components';
import { Foo } from 'src/app/secure/shared/api/model/foo/foo';
import { CreateResponse } from '@gae-web/appengine-api';
import { FooCreateActionDirective } from 'src/app/secure/shared/components/model/foo/client/create.directive';
import { MatProgressButtonOptions } from 'mat-progress-buttons';
import { UserLoginTokenService } from '@gae-web/appengine-token';

@Component({
  templateUrl: './list.component.html',
  encapsulation: ViewEncapsulation.None
})
export class ModelListComponent {

  @ViewChild(GaeListViewKeySearchSourceDirective, { static: true })
  private readonly _searchSource: GaeListViewKeySearchSourceDirective<Foo>;

  @ViewChild(FooCreateActionDirective, { static: true })
  private readonly _createAction: FooCreateActionDirective;

  private _ref: MatDialogRef<FooCreateDialogComponent>;

  constructor(private _dialog: MatDialog, private _tokenService: UserLoginTokenService) { }

  openCreateDialog() {
    if (!this._ref) {
      this._ref = this._dialog.open(FooCreateDialogComponent);

      this._ref.afterClosed().subscribe((result: CreateResponse<Foo> | undefined) => {
        if (result) {
          this._searchSource.refresh();
        }
      }).add(() => {
        this._ref = undefined;
      });
    }
  }

  createJunk() {
    this._createAction.testCreate(20).subscribe(() => {
      this._searchSource.refresh();
    });
  }

  test() {
    this._tokenService.getEncodedLoginToken().subscribe({
      next: (x) => {
        console.log('Token: ' + x);
      }
    });
  }

  get createJunkButtonOptions(): MatProgressButtonOptions {
    return {
      active: this._createAction.isWorking,
      text: 'Create 20 Items',
      buttonColor: 'accent',
      barColor: 'accent',
      raised: true,
      mode: 'indeterminate'
    };
  }

}
