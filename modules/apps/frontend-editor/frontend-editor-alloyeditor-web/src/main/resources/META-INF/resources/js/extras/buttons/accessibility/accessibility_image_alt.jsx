var React = AlloyEditor.React;

var AccessibilityImageAlt = React.createClass(
	{
		mixins: [AlloyEditor.ButtonStateClasses, AlloyEditor.ButtonCfgProps],

		displayName: 'AccessibilityImageAlt',

		propTypes: {
			editor: React.PropTypes.object.isRequired
		},

		statics: {
			key: 'imageAlt'
		},

		/**
		 * Lifecycle. Invoked once before the component is mounted.
		 * The return value will be used as the initial value of this.state.
		 *
		 * @method getInitialState
		 */
		getInitialState: function() {
			var image = this.props.editor.get('nativeEditor').getSelection().getSelectedElement();

			return {
				element: image,
				imageAlt: image ? image.getAttribute('alt') : ''
			};
		},

		/**
		 * Lifecycle. Renders the UI of the button.
		 * Rendering alt button or form to update the image´s alt depends on the renderExclusive property
		 *
		 * @method render
		 * @return {Object} The content which should be rendered.
		 */
		render: function() {
			var cssClass = 'ae-button ' + this.getStateClasses();

			var element;

			if (this.props.renderExclusive) {
				return (
					<div className="ae-container-edit-link">
						<div className="ae-container-input xxl">
							<input aria-label="alt" className="ae-input" onChange={this._handleAltChange} onKeyDown={this._handleKeyDown} placeholder="alt" ref="refAltInput" title="alt" type="text" value={this.state.imageAlt}></input>
						</div>
						<button aria-label={AlloyEditor.Strings.confirm} className="ae-button" onClick={this._updateImageAlt} title={AlloyEditor.Strings.confirm}>
							<span className="ae-icon-ok"></span>
						</button>
					</div>
				);
			}
			else {
				return (
					<button className={cssClass} onClick={this._requestExclusive} tabIndex={this.props.tabIndex}>
						<small className="ae-icon small">Alt</small>
					</button>
				);
			}

			return element;
		},

		/**
		 * Focuses the user cursor on the widget's input.
		 *
		 * @protected
		 * @method _focusAltInput
		 */
		_focusAltInput: function() {
			var instance = this;

			var focusAltEl = function() {
				AlloyEditor.ReactDOM.findDOMNode(instance.refs.refAltInput).focus();
			};

			if (window.requestAnimationFrame) {
				window.requestAnimationFrame(focusAltEl);
			}
			else {
				setTimeout(focusAltEl, 0);
			}
		},

		/**
		 * Event attached to alt input that fires when its value is changed
		 *
		 * @protected
		 * @method  _handleAltChange
		 * @param {MouseEvent} event
		 */
		_handleAltChange: function(event) {
			this.setState(
				{
					imageAlt: event.target.value
				}
			);

			this._focusAltInput();
		},

		/**
		 * Event attached to al tinput that fires when key is down
		 * This method check that enter key is pushed to update the component´s state
		 *
		 * @protected
		 * @method  _handleKeyDown
		 * @param {MouseEvent} event
		 */
		_handleKeyDown: function(event) {
			if (event.keyCode === 13) {
				event.preventDefault();

				this._updateImageAlt();
			}
		},

		/**
		 * Requests the link button to be rendered in exclusive mode to allow the creation of a link.
		 *
		 * @protected
		 * @method _requestExclusive
		 */
		_requestExclusive: function() {
			this.props.requestExclusive(AccessibilityImageAlt.key);
		},

		/**
		 * Method called by clicking ok button or pushing key enter to update imageAlt state and to update alt property from the image that is selected
		 * This method calls cancelExclusive to show the previous toolbar before enter to edit alt property
		 *
		 * @protected
		 * @method  _updateImageAlt
		 */
		_updateImageAlt: function() {
			var editor = this.props.editor.get('nativeEditor');

			var newValue = this.refs.refAltInput.value;

			this.setState(
				{
					imageAlt: newValue
				}
			);

			this.state.element.setAttribute('alt', newValue);

			editor.fire('actionPerformed', this);

			// We need to cancelExclusive with the bound parameters in case the button is used
			// inside another in exclusive mode (such is the case of the alt button)
			this.props.cancelExclusive();
		}
	}
);

export default AccessibilityImageAlt;
export {AccessibilityImageAlt};