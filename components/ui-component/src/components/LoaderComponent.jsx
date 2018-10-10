import React from 'react';
import { css } from 'react-emotion';
import PulseLoader from 'react-spinners/PulseLoader';

const override = css`
    display: block;
    margin: 0 auto;
    border-color: red;
    padding-top: 35px;
    margin-left: 10px;
`;

/**
 * Animated loader Component.
 */
export default class LoaderComponent extends React.Component {
    /**
     * @inheritdoc
     */
    constructor(props) {
        super(props);
        this.state = {
            loading: true,
        };
    }

    /**
     * @inheritdoc
     */
    render() {
        const { loading } = this.state;

        return (
            <div className='sweet-loading'>
                <PulseLoader
                    className={override}
                    sizeUnit='px'
                    size={5}
                    color='#507192'
                    loading={loading}
                />
            </div>
        );
    }
}
