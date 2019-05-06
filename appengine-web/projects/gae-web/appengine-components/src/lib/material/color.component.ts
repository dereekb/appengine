// tslint:disable:no-bitwise
import { ViewChild, Host, HostBinding, ViewEncapsulation, Input, Component, Directive, ElementRef, forwardRef } from '@angular/core';

export type ColorHex = string;

/**
 * Abstract colored element.
 */
export abstract class Colored {

    public setColorHex(color: ColorHex | undefined) {
        if (color) {
            if (color.charAt(0) !== '#') {
                color = '#' + color;
            }

            this.updateWithColorHexString(color);
        } else {
            this.clearColor();
        }
    }

    public abstract clearColor();

    protected abstract updateWithColorHexString(color: ColorHex);

}

@Directive({
    selector: '[gaeBackgroundColorStyle]',
    providers: [{ provide: Colored, useExisting: GaeBackgroundColorStyleDirective }]
})
export class GaeBackgroundColorStyleDirective extends Colored {

    @HostBinding('style.background-color')
    public style: string | undefined;

    public clearColor() {
        this.style = undefined;
    }

    protected updateWithColorHexString(color: ColorHex) {
        this.style = color;
    }

    /*
    @Input()
    public set gaeBackgroundColorStyle(color: string | undefined) {
        this.setColorHex(color);
    }
    */

}

@Directive({
    selector: '[gaeColorStyle]',
    providers: [{ provide: Colored, useExisting: GaeColorStyleDirective }]
})
export class GaeColorStyleDirective extends Colored {

    @HostBinding('style.color')
    public style: string | undefined;

    public clearColor() {
        this.style = undefined;
    }

    protected updateWithColorHexString(color: ColorHex) {
        this.style = color;
    }

}

/**
 * Image with a colored background.
 */
@Component({
    selector: 'gae-color-image',
    template: `
        <div class="gae-color-image" [ngClass]="{ 'gae-color-image-default': !src, 'gae-color-image-src': src }" gaeBackgroundColorStyle>
            <img *ngIf="src" src="{{src}}">
        </div>
    `,
    // styleUrls: ['../page.scss'],
    providers: [{ provide: Colored, useExisting: GaeColorImageComponent }],
    encapsulation: ViewEncapsulation.None
})
export class GaeColorImageComponent extends Colored {

    @Input()
    public src: string | undefined;

    @ViewChild(GaeBackgroundColorStyleDirective)
    public readonly bgStyle: GaeBackgroundColorStyleDirective;

    public clearColor() {
        this.bgStyle.clearColor();
    }

    protected updateWithColorHexString(color: ColorHex) {
        this.bgStyle.setColorHex(color);
    }

}

// MARK: Color Directives
/**
 * Used for converting a string to a hex color, and setting it as the background for a GaeColorImageComponent.
 */
@Directive({
    selector: '[gaeStringColor]'
})
export class GaeStringColorDirective {

    constructor(@Host() protected readonly image: Colored) { }

    @Input()
    public set gaeStringColor(value: string | undefined) {
        let color: string | undefined;

        if (value) {
            color = StringColorUtility.stringToColorHex(value);
        }

        this.image.setColorHex(color);
    }

}

export class StringColorUtility {

    static stringToColorHex(value: string): string {
        const result = this.numberToRGB(this.hashCode(value));
        return result;
    }

    static hashCode(str: number | string) {
        let hash = 0;

        str = String(str);

        for (let i = 0; i < str.length; i++) {
            hash = str.charCodeAt(i) + ((hash << 5) - hash);
        }

        return hash;
    }

    static numberToRGB(value: number) {
        const hex = (value & 0x00FFFFFF)
            .toString(16)
            .toUpperCase();

        return '00000'.substring(0, 6 - hex.length) + hex;
    }

}
