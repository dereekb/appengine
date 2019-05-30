import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild } from '@angular/core';
import { GaeMaterialComponentsModule } from './material.module';
import { GaePageToolbarComponent, GaePageToolbarConfigurationComponent, GaePageToolbarConfiguration, GaePageToolbarConfigurationProvider, ToolbarButtonNavType, GaePageToolbarNavButtonComponent } from './page-toolbar.component';
import { filter, first } from 'rxjs/operators';
import { By } from '@angular/platform-browser';


describe('GaePageToolbarComponent', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [GaeMaterialComponentsModule.forApp()],
      declarations: [TestViewComponent]
    }).compileComponents();
  }));

  let fixture: ComponentFixture<TestViewComponent>;
  let component: TestViewComponent;
  let toolbarComponent: GaePageToolbarComponent;

  beforeEach(() => {
    fixture = TestBed.createComponent(TestViewComponent);
    component = fixture.componentInstance;
    toolbarComponent = component.toolbarComponent;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
    expect(toolbarComponent).toBeTruthy();
  });

  describe('configuration', () => {

    const DEFAULT_TITLE = 'title';
    const defaultConfig: GaePageToolbarConfiguration = {
      title: DEFAULT_TITLE
    };

    beforeEach(() => {
      toolbarComponent.defaultConfiguration = defaultConfig;
    });

    it('should use the default configuration if there is no provider.', (done) => {
      toolbarComponent.stream.pipe(
        filter((x) => Boolean(x.title))
      ).subscribe((config) => {
        expect(config.title).toBe(DEFAULT_TITLE);
        done();
      });
    });

    describe('view', () => {

      const viewConfig: GaePageToolbarConfiguration = {
        left: {
          type: ToolbarButtonNavType.Basic,
          text: 'Left'
        },
        title: DEFAULT_TITLE,
        right: [{
          type: ToolbarButtonNavType.Basic,
          text: 'Basic'
        }, {
          type: ToolbarButtonNavType.Raised,
          text: 'Raised'
        }]
      };

      beforeEach(() => {
        toolbarComponent.defaultConfiguration = viewConfig;
        fixture.detectChanges();
      });

      it('should display the right button', () => {
        const toolbarButtons = fixture.debugElement.queryAll(By.directive(GaePageToolbarNavButtonComponent));
        const components: GaePageToolbarNavButtonComponent[] = toolbarButtons.map(x => x.componentInstance as GaePageToolbarNavButtonComponent);

        const left = components.find(x => x.text === 'Left');
        const basic = components.find(x => x.text === 'Basic');
        const raised = components.find(x => x.text === 'Raised');

        expect(left).toBeTruthy();
        expect(basic).toBeTruthy();
        expect(raised).toBeTruthy();
      });

    });

    describe('with providers', () => {

      const PROVIDER_A_TITLE = 'a';
      const PROVIDER_B_TITLE = 'b';

      let providerA: GaePageToolbarConfigurationProvider;
      let providerB: GaePageToolbarConfigurationProvider;

      beforeEach(() => {
        providerA = new GaePageToolbarConfigurationProvider({
          title: PROVIDER_A_TITLE
        });
        providerB = new GaePageToolbarConfigurationProvider({
          title: PROVIDER_B_TITLE
        });
      });

      it('should use the provider.', (done) => {
        toolbarComponent.addProvider(providerA);

        // Watch that eventually the provider is used.
        toolbarComponent.stream.pipe(
          filter((x) => x.title === PROVIDER_A_TITLE),
        ).subscribe((config) => {
          expect(config.title).toBe(PROVIDER_A_TITLE);
          done();
        });
      });

      it('should add and remove a provider.', (done) => {
        toolbarComponent.addProvider(providerA);

        // Watch that eventually the provider is used.
        toolbarComponent.stream.pipe(
          filter((x) => x.title === PROVIDER_A_TITLE),
          first()
        ).subscribe((config) => {
          expect(config.title).toBe(PROVIDER_A_TITLE);
          toolbarComponent.removeProvider(providerA);

          // Watch that eventually the provider is removed and the default title is restored.
          toolbarComponent.stream.pipe(
            filter((x) => x.title === DEFAULT_TITLE),
            first()
          ).subscribe((configB) => {
            expect(configB.title).toBe(DEFAULT_TITLE);
            done();
          });
        });
      });

      it('should return the latest provider.', (done) => {
        toolbarComponent.addProvider(providerA);
        toolbarComponent.addProvider(providerB);

        // Watch that eventually the provider B is used instead of A.
        toolbarComponent.stream.pipe(
          filter((x) => x.title === PROVIDER_B_TITLE),
        ).subscribe((config) => {
          expect(config.title).toBe(PROVIDER_B_TITLE);
          done();
        });
      });

    });

  });

});

@Component({
  template: `
  <gae-page-toolbar>
    <ng-container content>
      <gae-page-toolbar-configuration></gae-page-toolbar-configuration>
    </ng-container>
  </gae-page-toolbar>
  `
})
class TestViewComponent {

  @ViewChild(GaePageToolbarComponent)
  public toolbarComponent: GaePageToolbarComponent;

  @ViewChild(GaePageToolbarConfigurationComponent)
  public toolbarConfigurationComponent: GaePageToolbarConfigurationComponent;

}
