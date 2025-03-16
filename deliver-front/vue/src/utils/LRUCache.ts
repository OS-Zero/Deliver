export class LRUCache<K, V> {
	private capacity: number;
	private cache: Map<K, V>;
	constructor(capacity: number) {
		this.capacity = capacity;
		this.cache = new Map();
	}
	get(key: K) {
		if (this.cache.has(key)) {
			const val = this.cache.get(key)!;
			this.cache.delete(key);
			this.cache.set(key, val);
			return val;
		}
		return null;
	}
	put(key: K, value: V) {
		const val = this.get(key);
		if (val !== null) {
			this.cache.delete(key);
		}
		this.cache.set(key, value);
		if (this.cache.size > this.capacity) {
			this.cache.delete(this.cache.keys().next().value!);
		}
	}
}
