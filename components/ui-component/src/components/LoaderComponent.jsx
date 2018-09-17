import React from 'react';
import { css } from 'react-emotion';
import PulseLoader from 'react-spinners/PulseLoader';

const override = css`
    display: block;
    margin: 0 auto;
    border-color: red;
    position: absolute;
    top: 30px;
    right: 700px;
`;

export default class LoaderComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: true
        }
    }
    render() {
        return (
            <div className='sweet-loading'>
                <PulseLoader
                    className={override}
                    sizeUnit={"px"}
                    size={10}
                    color={'orange'}
                    loading={this.state.loading}
                />
            </div>
        )
    }
}