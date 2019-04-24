import 'isomorphic-unfetch';

export class SubtractionResult {
    constructor(public result: number) {
        this.result = result;
    }
}

export class SubtractionClient {
    constructor(private host: string) {
    }

    subtract(minuend: number, subtrahend: number) {
        return fetch(this.host + `/basic/subtraction`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json;charset=utf-8' },
            body: JSON.stringify({ minuend: minuend, subtrahends: [subtrahend] })
        })
            .then(response => response.json())
            .then(json => new SubtractionResult(json.result));
    }
}
