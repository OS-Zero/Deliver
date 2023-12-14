export function createNDArray(dimensions: Array<number>, initialValue: any) {
	if (dimensions.length === 0) {
		return initialValue
	}
	const currentDimension = dimensions[0]
	const restOfDimensions = dimensions.slice(1)
	return Array.from({ length: currentDimension }, () => createNDArray(restOfDimensions, initialValue))
}
