import { LazyCache } from './cache';

export interface MapRelinkingMergeConfig<A, B, M, K> extends MapRelinkingConfig<A, B, K> {
  merge: (b: B, a: A | undefined) => M;
}

export interface MapRelinkingConfig<A, B, K> {
  keyForA: (a: A) => K;
  keyForB: (b: B) => K;
}

export interface RelinkedModel<A, B> {
  item: B;
  relinked?: A;
}

export class RelinkingUtility {

  // MARK: Map Relinking
  static makeMapRelinkingMergeFunction<A, B, M, K>(items: A[], config: MapRelinkingMergeConfig<A, B, M, K>): (x: B) => M {
    const fn = this.makeMapRelinkingMergeFunctionBuilder(config)(items);
    return fn;
  }

  static makeMapRelinkingMergeFunctionBuilder<A, B, M, K>(config: MapRelinkingMergeConfig<A, B, M, K>): (items: A[]) => ((x: B) => M) {
    const builder = this.makeMapRelinkingFunctionBuilder(config);
    return (items: A[]) => {
      const relinkingFn = builder(items);
      return (x) => {
        const relinked = relinkingFn(x);
        return config.merge(relinked.item, relinked.relinked);
      };
    };
  }

  static makeMapRelinkingFunction<A, B, K>(items: A[], config: MapRelinkingConfig<A, B, K>): (x: B) => RelinkedModel<A, B> {
    const fn = this.makeMapRelinkingFunctionBuilder(config)(items);
    return fn;
  }

  static makeMapRelinkingFunctionBuilder<A, B, K>(config: MapRelinkingConfig<A, B, K>): (items: A[]) => ((x: B) => RelinkedModel<A, B>) {
    const fn: (items: A[]) => ((x: B) => RelinkedModel<A, B>) = (items: A[]) => {
      // Lazy-load the map when finally requested, instead of mapping initially. Might not be necessary.
      const mapLoader = new LazyCache<Map<K, A>>({
        refresh: () => {
          const map = new Map<K, A>();
          items.forEach((x) => map.set(config.keyForA(x), x));
          return map;
        }
      });

      return this.relinkingFunction((x) => {
        const key = config.keyForB(x);
        return mapLoader.value.get(key);
      });
    };
    return fn;
  }

  static relinkingFunction<A, B>(relink: (x: B) => A | undefined): (x: B) => RelinkedModel<A, B> {
    const fn = (x: B) => {
      const relinked = relink(x);
      return {
        item: x,
        relinked
      };
    };
    return fn;
  }

}
